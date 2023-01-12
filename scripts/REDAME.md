# IJ Code Server Scripts

## Requirements
* Python 3.9.6

## Set Up
1. Setup pytho env `source venv/bin/activate`
2. Run `pip install requirements.txt`

## Code Quality Analysis Client

### Set Up
1. Run proto classes generation
```commandline
python -m grpc_tools.protoc -I../ij-model/src/main/proto --python_out=. --pyi_out=. --grpc_python_out=. model.proto
```
