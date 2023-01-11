# IJ Code Server Scripts


## Code Quality

```commandline
python -m grpc_tools.protoc -I../ij-model/src/main/proto --python_out=. --pyi_out=. --grpc_python_out=. model.proto
```
