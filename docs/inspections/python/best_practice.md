## Best practice inspections

### General definition


**Best practice** issue means that your code does not follow the 
widely accepted recommendations and idioms of the language you’re using. 
Some features of the language can be used in an inefficient or obsolete way. 
Such errors are not critical but the fewer of them, the better.

### Enabled issues

<details>
  <summary>PyUnusedLocalInspection</summary>

Reports local variables, parameters, and functions that are locally defined, but not used name in a function.

Arguments (by default all are `true`):
- `ignoreTupleUnpacking` - ignore variables used in tuple unpacking
- `ignoreLambdaParameters` - ignore lambda parameters
- `ignoreLoopIterationVariables` - ignore range iteration variables **TODO: find example?**
- `ignoreVariablesStartingWithUnderscore` - ignore variables starting with `_`

Example:
```python
def foo():
    a = 5
    b = 10
    return b
```

Default descriptions:
- For parameters: `Parameter ''{0}'' value is not used`
- For variables: `Local variable ''{0}'' value is not used`
- For functions: `Local function ''{0}'' is not used`
- For classes: `Local class ''{0}'' is not used`
</details>

<details>
  <summary>PySimplifyBooleanCheckInspection</summary>

Reports equality comparison with a boolean literal.

Arguments (by default all are `true`):
- `ignoreComparisonToZero` - Ignore comparison to zero

Example:
```python
b = 5
if b != False:
    print(1)
```

Default description: `Expression can be simplified`
New description: `Expression can be simplified, e.g. "if a != False:" is the same with "if a:"`
</details>

<details>
  <summary>PyArgumentEqualDefaultInspection</summary>

Reports a problem when an argument passed to the function is equal to the default parameter value.

Example:
```python
def my_function(a: int = 2):
    print(a)


my_function(2)
```

Default description: `Argument equals to the default parameter value`
New description: `Argument equals to the default parameter value. You can delete the argument, the default parameter value will be used automatically.`
</details>

<details>
  <summary>PyAttributeOutsideInitInspection</summary>

Reports a problem when instance attribute definition is outside `__init__` method.

Example:
```python
class Book:
    def __init__(self):
        self.author = 'Mark Twain'

    def release(self):
        self.year = '1889'
```

Default description: `Instance attribute {0} defined outside __init__`
</details>

<details>
  <summary>PyBroadExceptionInspection</summary>

Reports exception clauses that do not provide specific information about the problem.

Example:
```python
x = '6'
try:
    if x > 3:
        print('X is larger than 3')
except Exception:
    print("Oops! x was not a valid number. Try again...")
```
or
```python
x = '6'
try:
    if x > 3:
        print('X is larger than 3')
except:
    print("Oops! x was not a valid number. Try again...")
```

Default description: `Too broad exception clause`
New description: `Please, specify the exception type, but avoid using too general exception "Exception"`
</details>

<details>
  <summary>PyDataclassInspection</summary>

Reports invalid definitions and usages of classes created with dataclasses or attr modules.

Examples with default descriptions:

**ERROR-PRONE**
1) `''{0}'' not supported between instances of ''{1}''`
New description: `''{0}'' not supported between instances of ''{1}''. You should add parameters into "dataclass" decorator: @dataclass(order=True).`
```python
from dataclasses import dataclass


@dataclass
class A:
    x: int = 10


a = A(1)
b = A(2)
print(a < b)
```
See [pep-0557](https://peps.python.org/pep-0557), the `order` block

**ERROR-PRONE**
2) `''{0}'' object could have no attribute ''{1}'' because it is declared as init-only`
Adapted description: `''{0}'' object could have no attribute ''{1}'' because it is declared as init-only. Please, don't call this attribute.`

```python
from __future__ import annotations
from dataclasses import dataclass, InitVar


@dataclass
class C:
    i: int
    init_only: InitVar[int | None] = None

    def __post_init__(self, init_only):
        if self.i is None and init_only is not None:
            self.i = init_only


c = C(10, init_only=5)
print(c.init_only)
```

See [Init only variables](https://docs.python.org/3/library/dataclasses.html#init-only-variables).

**ERROR-PRONE**
3) `''{0}'' object attribute ''{1}'' is read-only`
```python
from dataclasses import dataclass


@dataclass(frozen=True)
class A:
    i: int


a = A(5)
a.i = 10
```

**ERROR-PRONE**
4) `'eq' must be true if 'order' is true`
```python
from dataclasses import dataclass


@dataclass(eq=False, order=True)
class A:
    pass
```

**ERROR-PRONE**
5) `''{0}'' is ignored if the class already defines ''{1}'' method`, `''{0}'' is ignored if the class already defines ''{1}'' parameter`

```python
import dataclasses


@dataclasses.dataclass(repr=True)
class A:
    a: int = 1

    def __repr__(self):
        return "repr1"
```

**ERROR-PRONE**
6) `'order' should be False if the class defines one of order methods`
Adapted message: `'order' should be False if the class defines one of order methods: : __le__, __lt__, __gt__, __ge__`

```python
from dataclasses import dataclass


@dataclass(order=True)
class A:
    def __le__(self, other):
        pass
```

**ERROR-PRONE**
7) `'frozen' should be False if the class defines '__setattr__' or '__delattr__'`

```python
from dataclasses import dataclass


@dataclass(frozen=True)
class A:
    def __setattr__(self, key, value):
        pass
```

**ERROR-PRONE**
8) `'unsafe_hash' should be False if the class defines '__hash__'`

```python
from dataclasses import dataclass


@dataclass(unsafe_hash=True)
class A:
    def __hash__(self):
        pass
```

**ERROR-PRONE**
9) `Frozen dataclasses can not inherit non-frozen one and vice versa`

```python
from dataclasses import dataclass


@dataclass(frozen=True)
class A:
    pass


@dataclass
class B(A):
    pass
```

**ERROR-PRONE**
10) `'__hash__' is ignored if the class already defines 'cmp/order' and 'frozen' parameters`

```python
import attr


@attr.s(frozen=True)
class A3:

    def __hash__(self):
        pass


print(hash(A3()))
```

**ERROR-PRONE**
11) `Mutable default ''{0}'' is not allowed. Use ''default_factory''`
Adapted hint: `Mutable default ''{0}'' is not allowed. Use ''default_factory'', e.g. 'a: list = field(default_factory=list)'`

```python
from dataclasses import dataclass


@dataclass
class A:
    bar: list = []
```

**ERROR-PRONE**, DISABLE
12) `A default is set using ''{0}''`

```python
import attr


@attr.s
class AttrFactory:
    x = attr.ib(default=attr.Factory(int))

    @x.default
    def __init_x__(self):
        return 1
```

**ERROR-PRONE**, DISABLE
13) `''{0}'' should take only {1} {1,choice,1#parameter|2#parameters}`

```python
import attr


@attr.s
class A:
    x = attr.ib()

    @x.default
    def init_x2(self, attribute, value):
        return 10
```

**ERROR-PRONE**
14) `Attribute ''{0}'' lacks a type annotation`

```python
import dataclasses


@dataclasses.dataclass
class A1:
    a = dataclasses.field()
```

**ERROR-PRONE**
15) `Cannot specify both 'default' and 'factory'`

```python
from dataclasses import dataclass, field


@dataclass
class Pizza:
    meat: str = field(default='chicken', default_factory=['dow', 'tomatoes'])
```

**BEST PRACTICE**
16) `Attribute ''{0}'' is useless until ''__post_init__'' is declared`

```python
from __future__ import annotations
from dataclasses import dataclass, InitVar


@dataclass
class C:
    i: int
    init_only: InitVar[int | None] = None
```

**ERROR-PRONE**
17) `Field cannot have a default factory`

```python
from dataclasses import dataclass, InitVar, field
from typing import List


@dataclass
class A:
    a: InitVar[List[str]] = field(default_factory=list)

```

**ERROR-PRONE**
18) `'__post_init__' would not be called until 'init' parameter is set to True`

```python
from dataclasses import dataclass


@dataclass(init=False)
class A:
    def __post_init__(self):
        pass
```

**BEST PRACTICE**
19) `'__post_init__' should take all init-only variables (incl. inherited) in the same order as they are defined`

```python
from dataclasses import dataclass


@dataclass
class A:
    a: int
    b: str

    def __post_init__(self, b: str, a: int):
        pass
```

**BEST PRACTICE**
20) `'__attrs_post_init__' would not be called until 'init' parameter is set to True`
    `'__attrs_post_init__' should not take any parameters except 'self'`

```python
import attr

@attr.dataclass(init=False)
class A1:
    x: int = 0

    def __attrs_post_init__(self):
        pass
```

**ERROR-PRONE**
21) `''{0}'' method should be called on dataclass instances or types`, `''{0}'' method should be called on dataclass instances`, `''{0}'' method should be called on attrs instances`, `''{0}'' method should be called on attrs types`

```python
import dataclasses


class A:
    pass


dataclasses.fields(A)
```
</details>

<details>
  <summary>PyDictCreationInspection</summary>

Reports situations when you can rewrite dictionary creation by using a dictionary literal.

Example:
```python
dic = {}
dic['var'] = 1
```

Default description: `This dictionary creation could be rewritten as a dictionary literal`
Adapted message: `This dictionary creation could be rewritten as a dictionary literal, e.g. dic = {'var': 1}`
</details>

<details>
  <summary>PyFromFutureImportInspection</summary>

Reports from `__future__` import statements that are used not at the beginning of a file.

Example:
```python
a = 1

from __future__ import print_function

print()
```

Default description: `from __future__ imports must occur at the beginning of the file`
</details>

<details>
  <summary>PyReturnFromInitInspection</summary>

Reports occurrences of `return` statements with a return value inside `__init__` methods of classes.

Example:
```python
class Sum:
    def __init__(self, a, b):
        self.a = a
        self.b = b
        self.sum = a + b
        return self.sum
```

Default description: `Cannot return a value from __init__`
</details>

<details>
  <summary>PyListCreationInspection</summary>

Reports cases when a list declaration can be rewritten with a list literal.

Example:
```python
arr = [1]
arr.append(2)
```

Default description: `This list creation could be rewritten as a list literal`
Adapted message: `This list creation could be rewritten as a list literal, e.g. arr = [1, 2]`
</details>

<details>
  <summary>PySetFunctionToLiteralInspection</summary>

Reports calls to the `set` function that can be replaced with the `set` literal.

Example:
```python
def do_mult(a, b):
    c = a * b
    return set([c, a, b])
```

Default description: `Function call can be replaced with set literal`
Adapted message: `Function call can be replaced with set literal, e.g. set([c, a, b]) is the same with {c, a, b}`
</details>

<details>
  <summary>PyProtectedMemberInspection</summary>

Reports cases when a protected member is accessed outside the class,
a descendant of the class where it is defined, or a module.

1. Example:
```python
class A:
  def __init__(self):
    self._a = 1

  def foo(self):
    self.b= 1


print(A()._a)
```

Default descriptions: `Access to a protected member {0} of a class`, `Access to a protected member {0} of a module`
Adapted description: `Access to a protected member {0} of a class is not recommended`, `Access to a protected member {0} of a module is not recommended`

DISABLE
2. Example:
```python
# File 1
__all__ = ["m1m1"]


def m1m1():
    pass


def m1m2():
    pass
    
# File 2
from m1 import m1m2
```

Default description: `'{0}'' is not declared in __all__`
</details>

<details>
  <summary>PyMethodMayBeStaticInspection</summary>

Reports any methods that do not require a class instance creation and can be made static.

Example:
```python
class MyClass(object):
    def my_method(self, x):
        print(x)
```

Default description: `Method <code>#ref</code> may be 'static'`
Adapted message: `Method <code>#ref</code> may be 'static', because you don't use any properties of the class`
</details>

<details>
  <summary>PyChainedComparisonsInspection</summary>

Reports chained comparisons that can be simplified.

Example:
```python
def do_comparison(x):
    xmin = 10
    xmax = 100
    if x >= xmin and x <= xmax:
        pass
```

Default description: `Simplify chained comparison`
Adapted message: `Simplify chained comparison, , e.g 'if x >= a and x <= b:' is the same with 'a <= x <= b'`
</details>

### Disabled issues

<details>
  <summary>PyIncorrectDocstringInspection</summary>

Reports mismatched parameters in a docstring.

1. Example:
```python
def add(a, c):
    """ 
    @param a: 
    @return: 
    """
    pass
```

Default description: `Missing parameter {0} in docstring`

2. Example:
```python
def add(a, c):
    """ 
    @param a: 
    @param b:
    @return: 
    """
    pass
```

Default description: `Unexpected parameter {0} in docstring`
</details>

<details>
  <summary>PyMissingOrEmptyDocstringInspection</summary>

1. Example:
```python
def foo():
    """
    """
    pass
```

Default description: `Empty docstring`

2. Example:
```python
def foo():
    pass
```

Default description: `Missing docstring`
</details>

<details>
  <summary>PySingleQuotedDocstringInspection</summary>

Reports docstrings that do not adhere to the triple double-quoted string format.

Example:
```python
def calc(self, balance=0):
    'param: balance'
    self.balance = balance
```

Default description: `Triple double-quoted strings should be used for docstrings.`
</details>

<details>
  <summary>PyDocstringTypesInspection</summary>

Reports types in docstring that do not match dynamically inferred types.

Example:
```python
def foo(a):
    """ 
    @param a: 
    @type a: str
    @return: 
    """
    pass

print(foo(3))
```

Default description: `Dynamically inferred type ''{0}'' doesn''t match specified type ''{1}''`
</details>

<details>
  <summary>PyMissingTypeHintsInspection</summary>

Arguments (by default all are `true`):
- `m_onlyWhenTypesAreKnown` -  to check the types collected from runtime or inferred.

Reports missing type hints for function declaration in one of the two formats: parameter annotations or a type comment.

Default descriptions: `Type hinting is missing for a function definition`,
`Add type hints`, `Add type hints for ''{0}''`, `Only when types are known (collected from run-time or inferred)`

</details>
