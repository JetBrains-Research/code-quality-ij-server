import sys

import model_pb2 as model_pb2
import model_pb2_grpc as model_pb2_grpc

import grpc


class IJClient(object):
    def __init__(self, host: str = 'localhost', port: int = 1234):
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
    code = model_pb2.Code()

    code.text = "print(1, 2, 3)"
    code.languageId = model_pb2.LanguageId.Python

    client = IJClient()
    inspection_result = client.inspect(code)
    print(inspection_result)
