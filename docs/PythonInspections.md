# The list of Python inspections

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
</details>

<details>
  <summary>PyDefaultArgumentInspection</summary>

Reports a problem when a mutable value as a list or dictionary is detected in a default value for an argument.

Default argument values are evaluated only once at function definition time, which means that modifying 
the default value of the argument will affect all subsequent calls of that function.

Example:
```python
def f(args=[]):
    pass
```

Default description: `Default argument value is mutable`
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
</details>

<details>
  <summary>PyAssignmentToLoopOrWithParameterInspection</summary>

Checks for cases when you rewrite loop variable with inner loop. It finds all `with` and `for` statements, 
takes variables declared by them and ensures none of parent `with` or `for` declares variable with the same name.

Example:
```python
for i in range(5):
    for i in range(20, 25):
        print("Inner", i)
    print("Outer", i)
```

Default description: `Variable ''{0}'' is already declared in ''for'' loop or ''with'' statement above`
</details>

<details>
  <summary>PyAsyncCallInspection</summary>
**TODO: why this inspection does not work?**

Reports coroutines that were called without using the `await` syntax.

Example:
```python
async def bar():
    pass


async def foo():
    bar()
```

Default description: `Missing await syntax in coroutine calls`
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
  <summary>PyAugmentAssignmentInspection</summary>

Reports assignments that can be replaced with augmented assignments.

Example:
```python
a = 23
b = 3
a = a + b
```

Default description: `Assignment can be replaced with an augmented assignment`
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
</details>

<details>
  <summary>PyByteLiteralInspection</summary>

Reports characters in byte literals that are outside ASCII range.

Example:
```python
s = b'№5'
```

Default description: `Byte literal contains characters > 255`
</details>

<details>
  <summary>PyCallingNonCallableInspection</summary>

Reports a problem when you are trying to call objects that are not callable, like, for example, properties.

Example:
```python
class Record:
    @property
    def as_json(self): json = Record().as_json()
```

Default descriptions: 
- For objects: `'{0}'' object is not callable`
- For other cases: `''{0}'' is not callable`
- For expressions: `Expression is not callable`
</details>

<details>
  <summary>PyComparisonWithNoneInspection</summary>

Reports comparisons with `None`. That type of comparisons should always be done with is or is not, 
never the equality operators.

Example:
```python
a = 2
if a == None:
    print("Success")
```

Default description: `Comparison with None performed with equality operators`
</details>

<details>
  <summary>PyDataclassInspection</summary>

Reports invalid definitions and usages of classes created with dataclasses or attr modules.

Examples with default descriptions:

1) `''{0}'' not supported between instances of ''{1}''`
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

2) `''{0}'' object could have no attribute ''{1}'' because it is declared as init-only`
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

3) `''{0}'' object attribute ''{1}'' is read-only`
```python
from dataclasses import dataclass


@dataclass(frozen=True)
class A:
    i: int


a = A(5)
a.i = 10
```

4) `'eq' must be true if 'order' is true`
```python
from dataclasses import dataclass


@dataclass(eq=False, order=True)
class A:
    pass
```

5) `''{0}'' is ignored if the class already defines ''{1}'' method`, `''{0}'' is ignored if the class already defines ''{1}'' parameter`

```python
import dataclasses


@dataclasses.dataclass(repr=True)
class A:
    a: int = 1

    def __repr__(self):
        return "repr1"
```

6) `'order' should be False if the class defines one of order methods`

```python
from dataclasses import dataclass


@dataclass(order=True)
class A:
    def __le__(self, other):
        pass
```

7) `'frozen' should be False if the class defines '__setattr__' or '__delattr__'`

```python
from dataclasses import dataclass


@dataclass(frozen=True)
class A:
    def __setattr__(self, key, value):
        pass
```

8) `'unsafe_hash' should be False if the class defines '__hash__'`

```python
from dataclasses import dataclass


@dataclass(unsafe_hash=True)
class A:
    def __hash__(self):
        pass
```

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

10) `'__hash__' is ignored if the class already defines 'cmp/order' and 'frozen' parameters`

```python
import attr


@attr.s(frozen=True)
class A3:

    def __hash__(self):
        pass


print(hash(A3()))
```

11) `Mutable default ''{0}'' is not allowed. Use ''default_factory''`

```python
from dataclasses import dataclass


@dataclass
class A:
    bar: list = []
```

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

14) `Attribute ''{0}'' lacks a type annotation`

```python
import dataclasses


@dataclasses.dataclass
class A1:
    a = dataclasses.field()
```

15) `Cannot specify both 'default' and 'factory'`

```python
from dataclasses import dataclass, field


@dataclass
class Pizza:
    meat: str = field(default='chicken', default_factory=['dow', 'tomatoes'])
```

16) `Attribute ''{0}'' is useless until ''__post_init__'' is declared`

```python
from __future__ import annotations
from dataclasses import dataclass, InitVar


@dataclass
class C:
    i: int
    init_only: InitVar[int | None] = None
```

17) `Field cannot have a default factory`

```python
from dataclasses import dataclass, InitVar, field
from typing import List


@dataclass
class A:
    a: InitVar[List[str]] = field(default_factory=list)

```

18) `'__post_init__' would not be called until 'init' parameter is set to True`

```python
from dataclasses import dataclass


@dataclass(init=False)
class A:
    def __post_init__(self):
        pass
```

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
</details>

<details>
  <summary>PyDictDuplicateKeysInspection</summary>

Reports using the same value as the dictionary key twice.

Example:
```python
dic = {"a": [1, 2], "a": [3, 4]}
```
Note, the inspection indicates both cases and appears twice

Default description: `Dictionary contains duplicate keys ''{0}''`

</details>

<details>
  <summary>PyDunderSlotsInspection</summary>

Reports invalid usages of a class with __slots__ definitions.

1. Example:
```python
class C(object):
    __slots__ = ('x',)
    x = 0
```

Default description: `'{0}'' in __slots__ conflicts with a class variable`

2. Example:
```python
class Foo:
    __slots__ = ['foo', 'bar']

    
foo = Foo()
foo.baz = 'spam'
```

Default description: `''{0}'' object attribute ''{1}'' is read-only`
</details>

<details>
  <summary>PyExceptClausesOrderInspection</summary>

Report cases when except clauses are not in the proper order, 
from the more specific to the more generic, or one exception class is caught twice.

1. Example:
```python
def foo():
    pass


try:
    foo()
except Exception:
    pass
except Exception:
    pass
```

Default description: `Exception class ''{0}'' has already been caught`

2. Example:
```python
def foo():
    pass


try:
    foo()
except ValueError:
    pass
except UnicodeError:
    pass
```

Default description: `''{0}'', superclass of the exception class ''{1}'', has already been caught`
</details>

<details>
  <summary>PyFinalInspection</summary>

Reports invalid usages of final classes, methods and variables.

1. Example:
```python
from typing import final


@final
class A:
    pass


class B(A):
    pass
```

Default description: `{0} {1,choice,1#is|2#are} marked as ''@final'' and should not be subclassed`

2. Example:
```python
from typing import overload
from typing_extensions import final

class B:
    @overload
    def foo(self, a: int) -> int: ...

    @final
    @overload
    def foo(self, a: str) -> str: ...
```

Default description: `'@final' should be placed on the first overload` (only for stubs)

See - https://peps.python.org/pep-0591/

3. Example:
```python
from typing import final


class Dummy:
    @final
    def display(self):
        print("display from dummy")


class Demo(Dummy):
    def display(self):
        print("display from demo")
```

Default description: `''{0}'' is marked as ''@final'' and should not be overridden`

4. Example:
```python
from typing import overload, final


class Base:
    @overload
    def method(self, arg: int) -> int:
        pass

    @overload
    @final
    def method(self, x=None):
        pass
```

Default description: `'@final' should be placed on the implementation`

5. Example:
```python
from abc import ABCMeta, abstractmethod
from typing import final


class MyABC(metaclass=ABCMeta):
    @property
    @abstractmethod
    @final
    def my_abstract_property(self):
        ...
```

Default description: `'Final' could not be mixed with abstract decorators`

6. Example:
```python
from abc import ABCMeta, abstractmethod
from typing import final


@final
class MyABC(metaclass=ABCMeta):
    @property
    @abstractmethod
    def my_abstract_property(self):
        ...
```

Default description: `'Final' class could not contain abstract methods`

Note, the inspection indicates both cases and appears twice: `MyABC` and `my_abstract_property`

7. Example:
```python
from typing import final


@final
class MyABC:
    @final
    def foo(self):
        ...
```

Default description: `No need to mark method in 'Final' class as '@final'`

8. Example:
```python
from typing import final


@final
def foo():
    ...
```

Default description: `Non-method function could not be marked as '@final'`

9. Example:
```python
from typing import List, Final


def fun(x: Final[List[int]]) -> None:
    ...
```

Default description: `'Final' could not be used in annotations for function parameters`

10. Example:
```python
from typing import List, Final


def fun() -> Final[List[int]]:
    ...
```

Default description: `'Final' could not be used in annotation for a function return value`

11. Example:
```python
from typing_extensions import Final


class A:
    a: Final
```

Default description: `If assigned value is omitted, there should be an explicit type argument to 'Final'`

12. Example:
```python
from typing import Final


def fun():
    a: Final
```

Default description: `'Final' name should be initialized with a value`

13. Example:
```python
from typing import Final


class A:
    a: Final[int] = 1

    def __init__(self, a):
        self.a: Final[int] = a
```

Default description: `Already declared name could not be redefined as 'Final'`

14. Example:
```python
from typing import Final


class A:
    a: Final[int]

    def __init__(self):
        self.a: Final[str] = ""
```

Default description: `Either instance attribute or class attribute could be type hinted as 'Final'`

Note, the inspection indicates both cases and appears twice

15. Example:
```python
from typing import Final


class Mode:
    def __init__(self, title):
        self.a: Final[bool] = True


class Mode2(Mode):
    def __init__(self, title):
        super().__init__(title)
        self.a: Final[int] = 5
```

Default description: `''{0}'' is ''Final'' and could not be overridden`

16. Example:
```python
from typing import Final


class A:
    def foo(self):
        self.a: Final[int] = 5
```

Default description: `'Final' attribute should be declared in class body or '__init__'`

17. Example:
```python
from typing import Final


class Dummy:
    x: Final[int] = 1


class Demo(Dummy):
    x: str = ""

```

Default description: `''{0}'' is ''Final'' and could not be reassigned`

18. Example:
```python
from typing import Final, List


class A:
    a: List[Final] = 5
```

Default description: `'Final' could only be used as the outermost type`

19. Example:
```python
from typing import Final, List


class A:
    def foo(self):
        for i in range(0, 10):
            a: Final[int] = 5

```

Default description: `'Final' could not be used inside a loop`
</details>

<details>
  <summary>PyClassVarInspection</summary>

1. Example:
```python
from typing import ClassVar


class Cat:
    color: ClassVar[str] = "white"
    weight: int

    def __init__(self, weight: int):
        self.weight = weight
        Cat.color = "black"
        my_cat = Cat(5)
        my_cat.color = "gray"
```

Default description: `Cannot assign to class variable ''{0}'' via instance`

2. Example:
```python
from typing import ClassVar

color: ClassVar[str] = "white"
```

Default description: `'ClassVar' can only be used for assignments in class body`

3. Example:
```python
from typing import ClassVar


def foo():
    color: ClassVar[str] = "white"
```

Default description: `ClassVar' cannot be used in annotations for local variables`

4. Example:
```python
from typing import ClassVar


class A:
    color: ClassVar[str] = "white"


class B(A):
    color: int = 5
```

Default description: `Cannot override class variable ''{0}'' (previously declared on base class ''{1}'') with instance variable`

5. Example:
```python
from typing import ClassVar


class A:
    color: int = 5


class B(A):
    color: ClassVar[str] = "white"
```

Default description: `Cannot override instance variable ''{0}'' (previously declared on base class ''{1}'') with class variable`

6. Example:
```python
from typing import ClassVar


def foo(color: ClassVar[str]):
    pass
```

Default description: `'ClassVar' cannot be used in annotations for function parameters`

7. Example:
```python
from typing import ClassVar


def foo() -> ClassVar[str]:
    pass
```

Default description: `'ClassVar' cannot be used in annotation for a function return value`

8. Example:
```python
from typing import ClassVar, TypeVar, List

T = TypeVar("T")


class A:
    color: ClassVar[List[T]] = []
```

Default description: `'ClassVar' parameter cannot include type variables`
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
  <summary>PyGlobalUndefinedInspection</summary>

Reports problems when a variable defined through the `global` statement is not defined in the module scope.

Example:
```python
def foo():
    global bar
    print(bar)
    foo()
```

Default description: `Global variable ''{0}'' is undefined at the module level`
</details>

<details>
  <summary>PyInconsistentIndentationInspection</summary>

Reports inconsistent indentation in Python source files when, for example, you use a mixture of tabs and spaces in your code.

Default descriptions: 
- `Inconsistent indentation: mix of tabs and spaces`
- `Inconsistent indentation: previous line used tabs, this line uses spaces`
- `Inconsistent indentation: previous line used spaces, this line uses tabs`

</details>

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
  <summary>PyNamedTupleInspection</summary>

Reports invalid definition of a `typing.NamedTuple`.

Example:
```python
import typing

class FullName(typing.NamedTuple):
    first: str
    last: str = ""
    middle: str
```

Default description: `Fields with a default value must come after any fields without a default.`
</details>

<details>
  <summary>PyArgumentListInspection</summary>

Reports discrepancies between declared parameters and actual arguments, 
as well as incorrect arguments, for example, duplicate named arguments, and incorrect argument order.

1. Example:
```python
class Foo:
    def __call__(self, p1: int, *, p2: str = "%"):
        return p2 * p1


bar = Foo()
bar(5, "#")
```

Default description: `Unexpected argument`, `Unexpected argument(s)`

2. Example:
```python
class Foo:
    def __call__(self, p1: int, *, p2: str = "%"):
        return p2 * p1


bar = Foo()
bar.__call__()
```

Default description: `Parameter ''{0}'' unfilled`, `Parameter(s) unfilled`

3. Example:
```python
from typing import overload


@overload
def foo(value: None) -> None:
    pass

@overload
def foo(value: int) -> str:
    pass

@overload
def foo(value: str) -> str:
    pass


def foo(value):
    return None


foo()
```

Default description: `Possible callees`

4. Example:
```python
def baddeco(): 
    pass

@baddeco
```

Default description: `Function ''{0}'' lacks a positional argument`

5. Example:
```python
def foo(d: dict):
    pass

foo(5)
```

Default description: `Expected a dictionary, got {0}`, '`Expected an iterable, got {0}`'
</details>

<details>
  <summary>PyRedeclarationInspection</summary>

Reports unconditional redeclarations of names without being used in between.

Example:
```python
def x():
    pass

x = 2
```

Default description: `Redeclared ''{0}'' defined above without usage`
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

  def loo((l, g), *rest):
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
  <summary>PyMethodFirstArgAssignmentInspection</summary>

Reports cases when the first parameter, such as `self` or `cls`, 
is reassigned in a method. Because in most cases, there are no objectives in such reassignment,
class Account: def calc(self, balance): if balance == 0: self = balance return selfthe IDE indicates an error.

Example:
```python
class Account:
    def calc(self, balance):
        if balance == 0:
            self = balance
        return self
```

Default description: `Method''s parameter ''{0}'' reassigned`
</details>

<details>
  <summary>PyStringFormatInspection</summary>

Reports errors in string formatting operations.

1. Example:
```python
"%s %s" % {'a': 1, 'b': 2}
```

Default description: `Format does not require a mapping`

2. Example:
```python
"Hello {a}".format()
```

Default description: `Key ''{0}'' has no corresponding argument`

3. Example:
```python
print('%d %s cost $%.2f' % ('incorrect type', 'bananas', 1.74))
```

Default description: `Unexpected type {0}`

4. Example:
```python
print('%(name1s' % {'name1': 'a'})
```

Default description: `Too few mapping keys`

5. Example:
```python
val = "The percentage is 92.27"
print("%s%" % val)
```

Default description: `Format specifier character missing`

6. Example:
```python
print("%(name)f(name)" % 23.2)
```

Default description: `Format requires a mapping`

7. Example:
```python
val = "The percentage is 92.27"
print("s%%" % val)
```

Default description: `Too many arguments for format string`

8. Example:
```python
val = "The percentage is 92.27"
print("%s%% %s%%" % val)
```

Default description: `Too few arguments for format string`

9. Example:
```python
print("{:,s}".format(1))
```

Default description: `The format options in chunk "{0}" are incompatible`

10. Example:
```python
print('{:+q}; {:+f}'.format(3.14, -3.14))
```

Default description: `Unsupported format character ''{0}''`

11. Example:
```python
print('{1} {}'.format(6, 7))
```

Default description: `Cannot switch from manual field specification to automatic field numbering`

12. Example:
```python
print('{} {1}'.format(6, 7))
```

Default description: `Cannot switch from automatic field numbering to manual field specification`

13. Example:
```python
print('Hello %b!' % b'World')
```

Default description: `Unsupported format character 'b'`

14. Example:
```python
print('work%(name)*d' % (12, 32))
```

Default description: `Cannot use '*' in formats when using a mapping`
</details>

<details>
  <summary>PyMethodOverridingInspection</summary>

Reports inconsistencies in overriding method signatures.

Example:
```python
class Book:
    def add_title(self):
        pass

        
class Novel(Book):
    def add_title(self, text):
        pass
```

Default description: `Signature of method ''{0}'' does not match signature of the base method in class ''{1}''`
</details>

<details>
  <summary>PyInitNewSignatureInspection</summary>

Reports incompatible signatures of the `__new__` and `__init__` methods.

Example:
```python
class MyClass(object):
    def __new__(cls, arg1):
        return super().__new__(cls)

    def __init__(self):
        pass
```

Default descriptions: `Signature is not compatible to __init__`, `Signature is not compatible to __new__`
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
  <summary>PyTupleAssignmentBalanceInspection</summary>

Reports cases when the number of expressions on the right-hand side and targets on 
the left-hand side are not the same.

1. Example:
```python
*a, b = 1, 2
a, *b, c, *d = 1, 2, 3, 4, 5, 6
```

Default description: `Only one starred expression allowed in assignment`

2. Example:
```python
a, b = None
```

Default description: `Need more values to unpack`

3. Example:
```python
a, b = None, None, None
```

Default description: `Too many values to unpack`
</details>

<details>
  <summary>PyClassicStyleClassInspection</summary>

Reports classic style classes usage. This inspection applies only to Python 2.

Default descriptions: `Old-style class`, `Old-style class, because all classes from whom it inherits are old-style`

</details>

<details>
  <summary>PyExceptionInheritInspection</summary>

Reports cases when a custom exception class is raised but does not inherit from the 
builtin `Exception` class.

Example:
```python
class A:
    pass


def me_exception():
    raise A()
```

Default description: `Exception doesn't inherit from base 'Exception' class`
</details>

<details>
  <summary>PyUnboundLocalVariableInspection</summary>

Reports local variables referenced before assignment.

1. Example:
```python
def foo():
  var = "local"

  def bar():
    nonlocal var
    print(var)
    del var
    print(var)
```

Default description: `Local variable ''{0}'' might be referenced before assignment`

2. Example:
```python
def f1():
    nonlocal x
```

Default description: `Nonlocal variable ''{0}'' must be bound in an outer function scope`

3. Example:
```python
def foo() -> bool:
    pass


if foo(): 
    b = 1
print(b)
```

Default description: `Name ''{0}'' can be undefined`

4. Default description: `Function ''{0}'' is too large to analyse`

It appears if a `DFALimitExceededException` exception was thrown
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

</details>

<details>
  <summary>PySuperArgumentsInspection</summary>

Reports cases when any call to `super(A, B)` does not meet the following requirements:

- `B` is an instance of `A`
- `B` a subclass of `A`


Example:
```python
class Figure:
    def color(self):
        pass


class Rectangle(Figure):
    def color(self):
        pass


class Square(Figure):
    def color(self):
        return super(Rectangle, self).color()
```

Default description: `'{0}'' is not an instance or a subclass of ''{1}''`
</details>

<details>
  <summary>PyNonAsciiCharInspection</summary>

Reports cases in Python 2 when a file contains non-ASCII 
characters and does not have an encoding declaration at the top.

Default description: `Non-ASCII character ''{0}'' in the file, but no encoding declared`
</details>

<details>
  <summary>PyTupleItemAssignmentInspection</summary>

Reports assignments to a tuple item.

Example:
```python
t = ('red', 'blue', 'green', 'white')
t[3] = 'black'
```

Default description: `Tuples don't support item assignment`
</details>

<details>
  <summary>PyPropertyAccessInspection</summary>

Reports cases when properties are accessed inappropriately:

- Read-only properties are set
- Write-only properties are read
- Non-deletable properties are deleted

1. Example:
```python
class A(object):
    def s(self, v):
        self._v = v

    def g(self):
        return self._v

    def d(self):
        pass

    readonly = property(g)


a = A()
a.readonly += 1
```

Default description: `Property ''{0}'' cannot be set`

2. Example:
```python
class A(object):
    def s(self, v):
        self._v = v

    def g(self):
        return self._v

    def d(self):
        pass

    writeonly = property(None, s)


a = A()
a.writeonly += 1
```

Default description: `Property ''{0}'' cannot be read`

3. Example:
```python
class A(object):
    def s(self, v):
        self._v = v

    def g(self):
        return self._v

    def d(self):
        pass

    readonly = property(g)


a = A()
del a.readonly
```

Default description: `Property ''{0}'' cannot be deleted`
</details>

<details>
  <summary>PyPropertyDefinitionInspection</summary>

Reports problems with the arguments of `property()` and functions annotated with `@property`.

1. Example:
```python

```

**TODO: add example**

Default description: `The doc parameter should be a string`

2. Example:
```python

```

**TODO: add example**

Default description: `Strange argument; a callable is expected`

3. Example:
```python
import abc


class A(object):
  def __init__(self):
    self._x = 1
      
  @property
  def boo(self):
    return self._x

  @boo.setter
  def boo1(self, x):
    self._x = x
```

Default description: `Names of function and decorator don't match; property accessor is not created`

4. Example:
```python
import abc


class A:
    @property
    def normal_property(self):
        pass
```

Default description: `Getter should return or yield something`

5. Example:
```python
import abc


class A(object):
  def __init__(self):
    self._x = 1

  @property
  def moo(self):
    pass

  @moo.setter
  def moo(self, x):
    return 1
```

Default description: `Setter should not return a value`

6. Example:
```python
import abc


class A(object):
  def __init__(self):
    self._x = 1

  @property
  def moo(self):
    pass

  @moo.deleter
  def moo(self):
    return self._x
```

Default description: `Deleter should not return a value`

7. Example:
```python
class C:
    @property
    def abc(self):
        pass

    @abc.getter
    def abc(self, v1, v2): # Getter signature should be (self, value)
        pass
```

Default description: `Getter signature should be (self)`

8. Example:
```python
class C:
    @property
    def abc(self):
        pass

    @abc.setter
    def abc(self, v1, v2): # Setter signature should be (self, value)
        pass
```

Default description: `Setter signature should be (self, value)`

9. Example:
```python
class C:
    @property
    def abc(self):
        pass

    @abc.deleter
    def abc(self, v1): # Delete signature should be (self)
        pass
```

Default description: `Deleter signature should be (self)`
</details>

<details>
  <summary>PyNestedDecoratorsInspection</summary>

Reports problems with nesting decorators. 
The inspection highlights the cases when `classmethod` or `staticmethod` is applied 
before another decorator.

Example:
```python
def innocent(f):
    return f

class A:
    @innocent
    @classmethod
    def f2(cls):
        pass
```

Default description: `This decorator will not receive a callable it may expect; the built-in decorator returns a special object`
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
  <summary>PyCompatibilityInspection</summary>

Reports incompatibility with the specified versions of Python. 
Enable this inspection if you need your code to be compatible with a range of Python versions, 
for example, if you are building a library.

Probably we need to disable this inspection, because it includes a lot of errors, 
but usually students don't use old features
</details>

<details>
  <summary>PyListCreationInspection</summary>

Reports cases when a list declaration can be rewritten with a list literal.

Example:
```python
l = [1]
l.append(2)
```

Default description: `This list creation could be rewritten as a list literal`
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
  <summary>PyMissingConstructorInspection</summary>

Reports cases when a call to the `super` constructor in a class is missed.

Example:
```python
class Fruit:
    def __init__(self):
        pass

        
class Pear(Fruit):
    def __init__(self):
        pass
```

Default description: `Call to __init__ of super class is missed`
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
</details>

<details>
  <summary>PyDecoratorInspection</summary>

Reports usages of `@classmethod` or `@staticmethod` decorators in methods outside a class.

Example:
```python
class C:
  @classmethod
  def foo(self):
    pass

@classmethod
def foo(self):
  print("Constructor C was called")
```

Default description: `Decorator {0} on a method outside the class`
</details>

<details>
  <summary>PyTypeCheckerInspection</summary>

Reports type errors in function call expressions, targets, and return values. In a dynamically typed language, 
this is possible in a limited number of cases.

1. Example:
```python
from typing import TypedDict, List


class Point(TypedDict):
    x: int
    y: int


def a(x: List[int]) -> Point:
    return [x]
```

Default description: `Expected type ''{0}'', got ''{1}'' instead`

2. Example:
```python
from typing import TypedDict


class Point(TypedDict):
    x: int
    y: int


def d() -> Point:
    return {'x': 42, 'y': 42, 'k': 42}
```

Default description: `Extra key ''{0}'' for TypedDict ''{1}''`

3. Example:
```python
from typing import TypedDict


class Point(TypedDict):
    x: int
    y: int


def b(x: int) -> Point:
    return {'x': 42}
```

Default description: `TypedDict ''{0}'' has missing {1,choice,1#key|2#keys}: {2}`

4. Example:
```python
from typing import TypedDict


class Point(TypedDict):
    x: int
    y: int


def h(x) -> Point:
    x = 42
```

Default description: `Expected to return ''{0}'', got no return`

5. Example:
```python
class A:
    def __init__(self) -> int:
        pass
```

Default description: `__init__ should return None`

6. Example:
```python
class B1(type):
    meta_attr = "meta_attr"


class A1(metaclass=B1):
    pass


def print_unknown(a):
    print(a.unknown)


print_unknown(A1)
```

Default description: `Type ''{0}'' doesn't have expected {1,choice,1#attribute|2#attributes} {2}`

7. Default description: `Only a concrete class can be used where ''{0}'' (matched generic type ''{1}'') protocol is expected`

8. Example:
```python
from typing import Protocol, Type


class Proto(Protocol):
    def proto(self, i: int) -> None:
        pass


def foo(cls: Type[Proto]) -> None:
    pass


foo(Proto)
```

Default description: `Only a concrete class can be used where ''{0}'' protocol is expected`

9. Example:
```python
class User1(object):
    def __init__(self, x):
        """
        :type x: T
        :rtype: User1 of T
        """
        self.x = x

    def put(self, value):
        """
        :type value: T
        """
        self.x = value


c = User1(10)
c.put('foo')
```

Default description: `Expected type ''{0}'' (matched generic type ''{1}''), got ''{2}'' instead`

10. Example:
```python
import os.path


# not os.PathLike
class B:
    pass


b = B()

os.fspath(b)
```

Default descriptions: `Unexpected type(s):`, `Possible type(s):`

11. Example:
```python

```

**TODO: add examples**

Default descriptions: `Unexpected argument (from ParamSpec ''{0}'')`, `Parameter ''{0}'' unfilled (from ParamSpec ''{1}'')`
</details>

<details>
  <summary>PyDeprecationInspection</summary>

Reports usages of Python functions, or methods that are marked as deprecated 
and raise the `DeprecationWarning` or `PendingDeprecationWarning` warning.

Also, this inspection highlights usages of `abc.abstractstaticmethod`, `abc.abstractproperty`, 
and `abc.abstractclassmethod` decorators.

Example:
```python
class Foo:
    @property
    def bar(self):
        import warnings
        warnings.warn("this is deprecated", DeprecationWarning, 2)
        return 5

        
foo = Foo()
print(foo.bar)
```

Default description: `''{0}'' is deprecated since Python 3.3. Use ''{1}'' with ''{2}'' instead`, 
`this is deprecated`
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

<details>
  <summary>PyNoneFunctionAssignmentInspection</summary>

Reports cases when an assignment is done on a function that does not return anything.

This inspection is similar to pylint inspection [E1111](https://docs.pylint.org/#id6).

Example:
```python
def just_print():
    print("Hello!")


action = just_print()
```

Default description: `Function ''{0}'' doesn''t return anything`
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
</details>

<details>
  <summary>PyDocstringTypesInspection</summary>

Reports types in docstring that do not match dynamically inferred types.

Example:
```python

```

**TODO: add example**

Default description: `Dynamically inferred type ''{0}'' doesn''t match specified type ''{1}''`
</details>

<details>
  <summary>PyShadowingNamesInspection</summary>

Reports shadowing names defined in outer scopes.

Example:
```python
def outer(p):
    def inner(p):
        pass
```

Default description: `Shadows name {0} from outer scope`
</details>

<details>
  <summary>PyAbstractClassInspection</summary>

Reports cases when not all abstract properties or methods are defined in a subclass.

Example:
```python
from abc import abstractmethod, ABC

class Figure(ABC):
    @abstractmethod
    def do_figure(self):
        pass

class Triangle(Figure):
    def do_triangle(self):
        pass
```

Default description: `Class {0} must implement all abstract methods`
</details>

<details>
  <summary>PyMissingTypeHintsInspection</summary>

Arguments (by default all are `true`):
- `m_onlyWhenTypesAreKnown` -  to check the types collected from runtime or inferred.

Reports missing type hints for function declaration in one of the two formats: parameter annotations or a type comment.

Default descriptions: `Type hinting is missing for a function definition`, 
`Add type hints`, `Add type hints for ''{0}''`, `Only when types are known (collected from run-time or inferred)`

</details>

<details>
  <summary>PyOverloadsInspection</summary>

Reports cases when overloads in regular Python files are placed after the implementation 
or when their signatures are not compatible with the implementation.

1. Example:
```python
from typing import overload


class A:
    @overload
    def foo(self, value: None) -> None:
        pass

    @overload
    def foo(self, value: int) -> str:
        pass

    def foo(self, value):
        return None

    @overload
    def foo(self, value: str) -> str:
        pass
```

Default descriptions: `A series of @overload-decorated methods should always be followed by an implementation that is not @overload-ed`,
`A series of @overload-decorated functions should always be followed by an implementation that is not @overload-ed`

2. Example:
```python
from typing import overload


class A:
    @overload
    def foo(self) -> None:
        pass

    @overload
    def foo(self, value: str) -> str:
        pass

    def foo(self, value):
        return None
```

Default descriptions: `Signature of this @overload-decorated method is not compatible with the implementation`,
`Signature of this @overload-decorated function is not compatible with the implementation`

</details>

<details>
  <summary>PyProtocolInspection</summary>

Reports invalid definitions and usages of protocols introduced in [PEP-544](https://peps.python.org/pep-0544/).

1. Example:
```python
from typing import Protocol


class MyProto1(Protocol):
    pass


class A:
    pass


class D(A, MyProto1, Protocol):
    pass
```

Default description: `All bases of a protocol must be protocols`

2. Example:
```python
from typing import Protocol

class Closable2(Protocol):
    def close(self):
        pass


class ClosableImpl:
    def close(self):
        pass


assert isinstance(ClosableImpl(), Closable2)
```

Default description: `Only @runtime_checkable protocols can be used with instance and class checks`

3. Example:
```python
from typing import NewType, Protocol


class Id1(Protocol):
    code: int


UserId1 = NewType('UserId1', Id1)
```

Default description: `NewType cannot be used with protocol classes`

4. Example:
```python
from typing import Protocol


class MyProtocol(Protocol):
    attr: int

    def func(self, p: int) -> str:
        pass


class MyClass1(MyProtocol):
    def __init__(self, attr: int) -> None:
        self.attr = attr

    def func(self, p: str) -> int:
        pass
```

Default description: `Type of ''{0}'' is incompatible with ''{1}''`
</details>

<details>
  <summary>PyTypeHintsInspection</summary>

Reports invalid usages of type hints.

1. Example:
```python
def func(xs: list[int]):
    pass
```

**TODO: it does not work**

Default description: `Builtin ''{0}'' cannot be parameterized directly`

2. Example:
```python
from typing import Self, Generic, TypeVar

T = TypeVar("T")


class A(Generic[T]):
    def foo(self):
        x: Self[int]
```

Default description: `'Self' cannot be parameterized`

3. Example:
```python
class A:
    def method(self, i: int):
        v1: self.B
        v2 = None
        print(self.B)

    class B:
        pass
```

Default description: `Invalid type 'self'`

4. Example:
```python
from typing import Literal

a: Literal = 1
```

Default description: `'Literal' must have at least one parameter`

5. Example:
```python
from typing import Annotated

a: Annotated[1]
```

Default description: `'Annotated' must be called with at least two arguments`

6. Example:
```python
a : int = None  # type: int
```

Default description: `Types specified both in a type comment and annotation`

7. Example:
```python
from typing import List, TypeVar

T0 = TypeVar('T0')
a: List[T0]
b: List[TypeVar('T1')]
```

Default description: `A 'TypeVar()' expression must always directly be assigned to a variable`

8. Example:
```python
from typing import TypeVar

T0 = TypeVar('T0')
print(T0)
T0 = TypeVar('T0')
```

Default description: `Type variables must not be redefined`

9. Example:
```python
from typing import TypeVar

name = 'T0'
T0 = TypeVar(name)
```

Default description: `'TypeVar()' expects a string literal as first argument`

10. Example:
```python
from typing import ParamSpec

name = 'T0'
T0 = ParamSpec(name)
```

Default description: `'ParamSpec()' expects a string literal as first argument`

11. Example:
```python
from typing import TypeVar

T0 = TypeVar('T0')
T1 = TypeVar('T2')
```

Default description: `The argument to 'TypeVar()' must be a string equal to the variable name to which it is assigned`

12. Example:
```python
from typing import ParamSpec

T0 = ParamSpec('T1')
```

Default description: `The argument to 'ParamSpec()' must be a string equal to the variable name to which it is assigned`

13. Example:
```python
from typing import TypeVar

T1 = TypeVar('T1', contravariant=True, covariant=True)
```

Default description: `Bivariant type variables are not supported`

14. Example:
```python
from typing import TypeVar

T2 = TypeVar('T2', int, str, bound=str)
```

Default description: `Constraints cannot be combined with bound=…`

15. Example:
```python
from typing import TypeVar

T1 = TypeVar('T1', int)
```

Default description: `A single constraint is not allowed`

16. Example:
```python
from typing import TypeVar, List

T1 = TypeVar('T1', int, str)

T2 = TypeVar('T2', int, List[T1])
```

Default description: `Constraints cannot be parametrized by type variables`

17. Example:
```python
from typing import TypeVar

T = TypeVar("T")


class A:
    pass


assert isinstance(A(), T)
```

Default description: `Type variables cannot be used with instance and class checks`

18. Example:
```python
from typing import Union

class A:
 pass

assert isinstance(A(), Union)
```

Default description: `'{0}'' cannot be used with instance and class checks`, `Parameterized generics cannot be used with instance and class checks`

19. Example:
```python
from typing import Union


def a(b: Union(int, str)):
    pass
```

Default description: `Generics should be specified through square brackets`

20. Example:
```python
from typing import Generic


class A(Generic):
    pass
```

Default description: `Cannot inherit from plain 'Generic'`

21. Example:
```python
from typing import Generic, TypeVar

T = TypeVar('T')
S = TypeVar('S')


class C(Generic[T], Generic[S]):
    pass
```

Default description: `Cannot inherit from 'Generic[...]' multiple times`

22. Example:
```python
from typing import Generic, TypeVar, Iterable

T = TypeVar('T')
S = TypeVar('S')


class C(Generic[T], Iterable[S]):
    pass
```

Default description: `Some type variables ({0}) are not listed in ''Generic[{1}]''`

23. Example:
```python
from typing_extensions import Literal

a: Literal[1 + 2]
```

Default description: `'Literal' may be parameterized with literal ints, byte and unicode strings, bools, Enum values, None, other literal types, or type aliases to other literal types`

24. Example:
```python
from typing import Generic


class A1(Generic[0]):
    pass
```

Default description: `Parameters to 'Generic[...]' must all be type variables`

25. Example:
```python
from typing import Generic, TypeVar

T = TypeVar('T')


class C(Generic[T, T]):
    pass
```

Default description: `Parameters to 'Generic[...]' must all be unique`

26. Example:
```python
from typing import Callable

d: Callable[...]
```

Default description: `'Callable' must be used as 'Callable[[arg, ...], result]'`

27. Example:
```python
from typing import Callable

e: Callable[int, str]
```

**We can see this inspection inside IDE, byt by some reason we can not see it through API**

Default description: `'Callable' first parameter must be a parameter expression`

28. Example:
```python
from typing import Callable

foo1: Callable[[int], [int]] = None
```

Default description: `Parameters to generic types must be types`

29. Example:
```python
def undefined() -> int:
    pass

a1 = undefined()  # type: int

b2, (c2, d2) = undefined()  # type: int, (int)
```

**We can see this inspection inside IDE, byt by some reason we can not see it through API**

Default description: `Type comment cannot be matched with unpacked variables`

30. Example:
```python
class Bar:
    def egg12(self, a, b):
         # type: (Bar) -> None
        pass
```

Default description: `Type signature has too few arguments`

31. Example:
```python
class Bar:
    def spam1(self):
        # type: (Bar, int) -> None
        pass
```

Default description: `Type signature has too many arguments`

32. Example:
```python
class Bar:
    def spam2(self):
        # type: (int) -> None
        pass
```

Default description: `The type of self ''{0}'' is not a supertype of its class ''{1}''`

33. Example:
```python
class A:
    def method(self, b):
        b.a: int = 1
```

Default description: `Non-self attribute could not be type hinted`

33. Example:
```python
from typing import TypeAlias

Alias: TypeAlias[int]
```

Default description: `'TypeAlias' must be used as standalone type hint`

34. Example:
```python
from typing import TypeAlias

Alias: TypeAlias
```

Default description: `Type alias must be immediately initialized`

35. Example:
```python
from typing import TypeAlias

def func():
   Alias: TypeAlias = str
```

Default description: `Type alias must be top-level declaration`

36. Example:
```python
from typing import TypeAlias

Alias = TypeAlias[int]
```

Default description: `'TypeAlias' cannot be parameterized`

37. Example:
```python
from __future__ import annotations
from typing import Self


class SomeClass:
    @staticmethod
    def foo(bar: Self) -> Self:
        return bar
```

Default description: `Cannot use 'Self' in staticmethod`

38. Example:
```python
from typing import Self


def foo() -> Self:
    pass
```

Default description: `Cannot use 'Self' outside class`

39. Example:
```python
from __future__ import annotations
from typing import Self


class SomeClass:
    def foo(self: SomeClass, bar: Self) -> Self:
        return self
```

Default description: `Cannot use 'Self' if 'self' parameter is not 'Self' annotated`

40. Example:
```python
from __future__ import annotations
from typing import Self


class SomeClass:
    @classmethod
    def foo(cls: SomeClass, bar: Self) -> Self:
        return self
```

Default description: `Cannot use 'Self' if 'cls' parameter is not 'Self' annotated`
</details>

<details>
  <summary>PyTypedDictInspection</summary>

Reports invalid definition and usage of TypedDict.

1. Example:
```python
from typing import TypedDict


class Movie(TypedDict):
   name: str
   year: int


year = 'year'
year2 = year
m = Movie(name='Alien', year=1979)
years_since_epoch = m[year2] - 1970
year = 42
print(m[year])
```

Default description: `TypedDict key must be a string literal; expected one of ({0})`

2. Example:
```python
from typing import TypedDict


class X(TypedDict):
    x: int


x = X()
x.get('y', 67)
```

Default descriptions: `TypedDict "{0}" has no key ''{1}''`, `TypedDict "{0}" has no keys ({1})`

3. Example:
```python
from typing import TypedDict

Movie2 = TypedDict('Movie', {'name': str, 'year': int}, total=False)
```

Default description: `First argument has to match the variable name`

4. Example:
```python
from typing import TypedDict, NamedTuple


class Bastard:
    pass


class X(TypedDict):
    x: int


class XYZ(X, Bastard):
    z: bool
```

Default description: `TypedDict cannot inherit from a non-TypedDict base class`

5. Example:
```python
from typing import TypedDict

class Movie(TypedDict, metaclass=Meta):
   name: str
```

Default description: `Specifying a metaclass is not allowed in TypedDict`

6. Example:
```python
from typing import TypedDict


class X(TypedDict):
    y: int


class Y(TypedDict):
    y: str


class XYZ(X, Y):
    y: bool
```

Default descriptions: `Cannot overwrite TypedDict field ''{0}'' while merging`, `Cannot overwrite TypedDict field`

7. Example:
```python
from typing import TypedDict


class Movie(TypedDict):
    name: str

    def my_method(self):
        pass


class Horror:
    def __init__(self):
        ...
```

Default description: `Invalid statement in TypedDict definition; expected 'field_name: field_type'`

8. Example:
```python
from typing import TypedDict


class Movie(TypedDict):
    name: str
    year: int = 42
```

Default description: `Right-hand side values are not supported in TypedDict`

9. Example:
```python
from typing import TypedDict


class Movie(TypedDict):
    name: str
    year: int


class HorrorMovie(Movie, total=False):
    based_on_book: bool


year = 'year'
year2 = year
m = HorrorMovie(name='Alien', year=1979)
del (m['based_on_book'], m['name'])
```

Default description: `Key ''{0}'' of TypedDict ''{1}'' cannot be deleted`

10. Example:
```python
from typing import TypedDict


class Movie(TypedDict):
    name: str
    year: int


class Horror(Movie, total=False):
    based_on_book: bool


m = Horror(name='Alien', year=1979)
m.clear()
```

Default description: `This operation might break TypedDict consistency`

11. Example:
```python
from typing import TypedDict


class X(TypedDict):
    x: int


x = X()
x.get(42, 67)
```

Default description: `Key should be string`

12. Example:
```python
from typing import TypedDict, Any, Optional


class Movie(TypedDict):
    name: Optional[int]
    smth: type
    smthElse: Any
    year: 2
```

Default description: `Value must be a type`

13. Example:
```python
from typing import TypedDict


class X(TypedDict, total=1):
    x: int
```

Default description: `Value of 'total' must be True or False`

14. Example:
```python
from typing import TypedDict, Optional


class Movie(TypedDict):
    name: str
    year: Optional[int]


class Horror(Movie, total=False):
    based_on_book: bool


m = Horror(name='Alien', year=1979)
d = {'name': 'Garden State', 'year': 2004}
m.update(d)
m.update({'name': 'Garden State', 'year': '2004', 'based_on': 'book'})
```

Default description: `TypedDict "{0}" cannot have key ''{1}''`

15. Example:
```python

```

**TODO: add example**

Default description: `Cannot add a non-string key to TypedDict "{0}"`

16. Example:
```python
from typing_extensions import Required

x: Required[int]
```

Default description: `''{0}'' can be used only in a TypedDict definition`

17. Example:
```python
from typing_extensions import TypedDict, Required, NotRequired


class A(TypedDict):
    x: Required[NotRequired[int]]
    y: Required[int]
    z: NotRequired[int]
```

Default description: `Key cannot be required and not required at the same time`

18. Example:
```python
from typing_extensions import TypedDict, Annotated, Required, NotRequired

Alternative = TypedDict("Alternative", {'x': Annotated[Required[int], "constraint"],
                                        'y': NotRequired[Required[int], "constraint"]})
```

Default description: `''{0}'' must have exactly one type argument`
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

5. Example:
```python
from x import TEST as test
```

Default description: `Constant variable imported as non-constant`

6. Example:
```python
from x import y as TEST
```

Default descriptions: `Lowercase variable imported as non-lowercase`, `CamelCase variable imported as lowercase`, 
`CamelCase variable imported as constant`
</details>

<details>
  <summary>PyShadowingBuiltinsInspection</summary>

Reports shadowing built-in names, such as `len` or `list`.

1. Example:
```python
def len(a, b, c):
    d = a + b + c
    return d
```

Default description: `Shadows built-in name ''{0}''`
</details>


<details>
  <summary>PyUnresolvedReferencesInspection</summary>

Reports references in your code that cannot be resolved.

1. Example:
```python
def print_string():
    print(s.abc())
```

Default description: `Unresolved reference ''{0}''`

2. Example:
```python
def f(x):
    try:
        from foo import StringIO
    except Exception:
        pass
    return x
```

Default descriptions: `Module ''{0}'' not found`, `No module named ''{0}''`

3. Example:
```python
from io import BytesIO

fd = BytesIO(b'foo')
fd.foo()
```

Default description: `Unresolved attribute reference ''{0}'' for class ''{1}''`

4. Example:
```python
class MyClass(object):
    def method(self):
        pass

    @staticmethod
    def static_method():
        pass


MyClass.method.__defaults__
```

Default description: `Cannot find reference ''{0}'' in ''{1}''`

5. Example:
```python
def assign1():
    class B(object):
        __slots__ = ['foo']

    b = B()
    b.bar = 1
```

Default description: `'{0}'' object has no attribute ''{1}''`

6. Example:

Default description: `Import resolves to its containing file`

7. Example:
```python
class Foo(object):
    def __getitem__(self, item):
        return item

a = Foo[0]
```

Default description: `Class ''{0}'' does not define ''{1}'', so the ''{2}'' operator cannot be used on its instances`

8. Example:
```python

```

**TODO: add example**

Default description: `Function ''{0}'' does not have a parameter ''{1}''`
</details>

<details>
  <summary>PyTestParametrizedInspection</summary>

Reports functions that are decorated with `@pytest.mark.parametrize` but do not have arguments 
to accept parameters of the decorator.

Default description: `Incorrect arguments in @pytest.mark.parametrize`
</details>

<details>
  <summary>PyPep8Inspection</summary>

Reports violations of the PEP 8 coding style guide by running the bundled [pycodestyle.py](https://github.com/PyCQA/pycodestyle) tool.

Use a special config to run the tool.

**TODO: add the final config**
</details>

<details>
  <summary>PyInterpreterInspection</summary>

Reports problems if there is no Python interpreter configured for the project or if the interpreter is invalid. 
Without a properly configured interpreter, you cannot execute your Python scripts and benefit 
from some Python code insight features.
</details>

<details>
  <summary>PyStubPackagesCompatibilityInspection</summary>

Reports stub packages that do not support the version of the corresponding runtime package.
</details>

<details>
  <summary>PyStubPackagesAdvertiser</summary>

Reports availability of stub packages.
</details>


<details>
  <summary>PyRelativeImportInspection</summary>

Reports usages of relative imports inside plain directories, 
for example, directories neither containing '__init__.py' nor explicitly marked as namespace packages.
</details>