## Code style inspections

### General definition

**Code style** issue means that your code violates one of the rules recorded in the style guide 
for the language you’re using. It is highly recommended to fix such issues 
so that your code style matches that of other developers.

### Enabled issues

<details>
  <summary>PyRedundantParenthesesInspection</summary>

Reports about redundant parentheses in expressions.

Arguments (by default all are `false`):
- `ignoreEmptyBaseClasses` - ignore empty lists of base classes
- `ignoreTupleInReturn` - ignore tuples in return
- `ignorePercOperator` - ignore argument of % operator

Example:
```python
if (True):
    print(1)
```

Default description: `Remove redundant parentheses`
</details>

<details>
  <summary>PyAugmentAssignmentInspection</summary>

Reports assignments that can be replaced with augmented assignments.

Example:
```python
a = 23
b = 3
a = a + b
```

Default description: `Assignment can be replaced with an augmented assignment`
Adapted hint: `Assignment can be replaced with an augmented assignment, e.g 'a = a + b' is the same with 'a += b'`
</details>

<details>
  <summary>PyMethodParametersInspection</summary>

Reports methods that lack the first parameter that is usually named self.
The inspection also reports naming issues in class methods.

1. Example:
```python
class Movie:
    def show():
        pass
```

Default description: `Method must have a first parameter, usually called ''{0}''`,
`Usually first parameter of a method is named 'self'`,
`Usually first parameter of such methods is named ''{0}''`

2. Example:
```python
class Movie:
    def show(sself):
        pass
```

Default description: `Did not you mean 'self'?`

Note: this inspection uses the following list of words with typos: `{"eslf", "sself", "elf", "felf", "slef", "seelf", "slf", "sslf", "sefl", "sellf", "sef", "seef"}`

3. Example:
```python
class Foo(object): 

  def loo((f, g), *rest):
    pass # complain at tuple
```

Default description: `First parameter of a non-static method must not be a tuple`
</details>

<details>
  <summary>PyUnreachableCodeInspection</summary>

Reports code fragments that cannot be normally reached.

Example:
```python
if True:
    print('Yes')
else:
    print('No')
```

Default description: `This code is unreachable`
</details>


<details>
  <summary>PyTrailingSemicolonInspection</summary>

Reports trailing semicolons in statements.def my_func(a): c = a ** 2; return c

Example:
```python
def my_func(a):
    c = a ** 2;
    return c
```

Default description: `Trailing semicolon in the statement`
Adapted message: `You don't need to use a semicolon here`
</details>

<details>
  <summary>PyStatementEffectInspection</summary>

Reports statements that have no effect.

Example:
```python
class Car:
    def __init__(self, speed=0):
        self.speed = speed
        self.time
```

Default description: `Statement seems to have no effect`
Adapted description: `You can delete this statement since it has no effect`

</details>

<details>
  <summary>PyPep8NamingInspection</summary>

Reports violations of the [PEP8](https://peps.python.org/pep-0008/) naming conventions.

1. Example:
```python
class cls:
    pass
```

Default description: `Class names should use CamelCase convention`

2. Example:
```python
class A:
    def FuNc(self):
        pass
```

Default description: `Function name should be lowercase`

3. Example:
```python
class A:
    def foo(self, Arg):
        pass
```

Default description: `Argument name should be lowercase`

4. Example:
```python
from collections import namedtuple

def f():
    Point = namedtuple("Point", ["x1", "x2"], verbose=True)
    Test = "foo"
```

Default description: `Variable in function should be lowercase`

DISABLE
5. Example:
```python
from x import TEST as test
```

Default description: `Constant variable imported as non-constant`

DISABLE
6. Example:
```python
from x import y as TEST
```

Default descriptions: `Lowercase variable imported as non-lowercase`, `CamelCase variable imported as lowercase`,
`CamelCase variable imported as constant`
</details>

<details>
  <summary>PyPep8Inspection</summary>

Reports violations of the PEP 8 coding style guide by running the bundled [pycodestyle.py](https://github.com/PyCQA/pycodestyle) tool.

Use a special config to run the tool.

**TODO: add the final config**
</details>

### Disabled issues

<details>
  <summary>PyInconsistentIndentationInspection</summary>

Reports inconsistent indentation in Python source files when, for example, you use a mixture of tabs and spaces in your code.

Default descriptions:
- `Inconsistent indentation: mix of tabs and spaces`
- `Inconsistent indentation: previous line used tabs, this line uses spaces`
- `Inconsistent indentation: previous line used spaces, this line uses tabs`

</details>

<details>
  <summary>PyClassicStyleClassInspection</summary>

Reports classic style classes usage. This inspection applies only to Python 2.

Default descriptions: `Old-style class`, `Old-style class, because all classes from whom it inherits are old-style`

</details>

<details>
  <summary>PyNonAsciiCharInspection</summary>

Reports cases in Python 2 when a file contains non-ASCII
characters and does not have an encoding declaration at the top.

Default description: `Non-ASCII character ''{0}'' in the file, but no encoding declared`
</details>

<details>
  <summary>PyOldStyleClassesInspection</summary>

Reports occurrences of new-style class features in old-style classes.
The inspection highlights `__slots__`, `__getattribute__`, and `super()` inside old-style classes.

Example:
```python
class A:
    def __getattribute__(self):
        pass
```

Default descriptions: `Old-style class contains __getattribute__ definition`,
`Old-style class contains __slots__ definition`, `Old-style class contains call for super method`

</details>

<details>
  <summary>PyUnnecessaryBackslashInspection</summary>

Reports backslashes in places where line continuation is implicit inside `()`, `[]`, and `{}`.

Example:
```python
if (True \
    or True \
    or False):
  print("false")
```

Default description: `Unnecessary backslash in the expression`
</details>

<details>
  <summary>PyMandatoryEncodingInspection</summary>

Reports a missing encoding comment in Python 2.
</details>

<details>
  <summary>PyClassHasNoInitInspection</summary>

Reports cases in Python 2 when a class has no ]__init__] method, neither its parent classes.

Default description: `Class has no __init__ method`
</details>

