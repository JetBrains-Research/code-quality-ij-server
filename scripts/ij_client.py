import argparse
import sys
from pathlib import Path

import model_pb2 as model_pb2
import model_pb2_grpc as model_pb2_grpc

import grpc


class IJClient(object):
    def __init__(self, host: str = 'localhost', port: int = 8080):
        self.host = host
        self.port = port

        # instantiate a channel
        self.channel = grpc.insecure_channel(f'{self.host}:{self.port}')

        # bind the client and the server
        try:
            grpc.channel_ready_future(self.channel).result(timeout=10)
        except grpc.FutureTimeoutError:
            sys.exit('Error connecting to server')
        else:
            self.stub = model_pb2_grpc.CodeInspectionServiceStub(self.channel)

    def inspect(self, code: model_pb2.Code) -> model_pb2.InspectionResult:
        return self.stub.inspect(code)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('code_path', type=lambda value: Path(value).absolute(), help='Path to file with code sample.')
    parser.add_argument('language', choices=model_pb2.LanguageId.keys(), help='Language of code sample.')
    parser.add_argument('--host', help='Server host name', default='localhost')
    parser.add_argument('--port', type=int, help='Server port number', default=8080)
    args = parser.parse_args()

    code = model_pb2.Code()
    code.languageId = model_pb2.LanguageId.Value(args.language)
    with open(args.code_path) as f:
        code_sample = f.read()
        code.text = code_sample

    client = IJClient(args.host, args.port)
    inspection_result = client.inspect(code)
    print(inspection_result)
