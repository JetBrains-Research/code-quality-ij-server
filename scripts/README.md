# IJ Code Server Scripts

## Requirements
* Python 3.9.6

## Set Up
1. Setup pytho env 
```commandline
source venv/bin/activate
```
2. Install requirements
```commandline
pip install requirements.txt
```

## Code Quality Analysis Client

### Set Up
1. Run proto classes generation
```commandline
python -m grpc_tools.protoc -I../ij-model/src/main/proto --python_out=. --pyi_out=. --grpc_python_out=. model.proto
```
2. Run code inspections client on sample code and get result to console
```commandline
python -m ij_client path/to/code/sample
```
