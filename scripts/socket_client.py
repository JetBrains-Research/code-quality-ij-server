import sys

import model_pb2 as model_pb2
import model_pb2_grpc as model_pb2_grpc

import grpc

if __name__ == '__main__':
    code = model_pb2.Code()

    code.text = "print(1, 2, 3)"
    code.languageId = model_pb2.LanguageId.Python

    print(code.SerializeToString())

    channel = grpc.insecure_channel('localhost:1234')

    try:
        grpc.channel_ready_future(channel).result(timeout=10)
    except grpc.FutureTimeoutError:
        sys.exit('Error connecting to server')
    else:
        stub = model_pb2_grpc.CodeInspectionServiceStub(channel)

    result = stub.inspect(code)
    print(result)
