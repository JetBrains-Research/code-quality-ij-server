# IJ Code Server Scripts

## Requirements
* Python 3.9.6

## Set Up

1. Setup Python venv 
```commandline
source venv/bin/activate
```
2. Install requirements
```commandline
pip install -r requirements.txt
```

## Code Quality Analysis Client

### Set Up
1. Run proto classes generation
```commandline
python3 -m grpc_tools.protoc -I../ij-model/src/main/proto --python_out=. --pyi_out=. --grpc_python_out=. model.proto
```
2. Run code inspections client on sample code and get result to console
```commandline
python3 -m ij_client path/to/code/sample
```
