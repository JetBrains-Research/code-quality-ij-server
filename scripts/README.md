# IJ Code Server Scripts

## Requirements
* Python 3.9.6

## Set Up Environment


1. Create a new virtual environment:
   ```bash
   python -m venv venv
   ```

2. Activate the Python venv
   ```bash
   source venv/bin/activate
   ```

3. Install the requirements
   ```bash
   pip install -r requirements.txt
   ```

## Code Quality Analysis Client

### Set Up
1. Run proto classes generation
   ```bash
   python3 -m grpc_tools.protoc -I../ij-model/src/main/proto --python_out=. --pyi_out=. --grpc_python_out=. model.proto
   ```
2. Run code inspections client on sample code and get result to console
   ```bash
   python3 -m ij_client path/to/code/sample
   ```
