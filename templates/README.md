# Templates

This folder contains predefined templates for different languages. 
Before running the server you should additionally set up templates for languages that you want to use.
The supported languages are listed below:
- [Python](#python-project-template)
- [Kotlin](#kotlin-project-template)

## Python project template

1. Go to the `python` folder:
   ```bash
   cd ./python
   ```

2. Create a new virtual environment:
   ```bash
   python -m venv venv
   ```

3. Activate the Python venv
   ```bash
   source venv/bin/activate
   ```

4. Install the requirements
   ```bash
   pip install -r requirements.txt
   ```

> **Note**
> 
> You could also use your own template project:
> - It must be a correct Python project that PyCharm could open 
>   (you could find the PyCharm API version in [gradle.properties](../gradle.properties)).
> - It must have the `main.py` file to which the code sent for analysis will be saved.
> - It must have a virtual environment folder in the root of the template with the name `venv`.

## Kotlin project template
No additional setup is required.

> **Note**
>
> You could also use your own template project:
> - It must be a correct Kotlin project that IntelliJ could open.
>   (you could find the IJ API version in [gradle.properties](../gradle.properties)).
> - It must have the `Main.kt` file to which the code sent for analysis will be saved.
