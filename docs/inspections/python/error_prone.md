## Error-prone inspections

#### General definition

**Error-prone** issue means that your code contains a potential bug. 
Even if your code passes our automatic tests, it may behave incorrectly in some cases, 
which would be a problem in a real project. You should always fix such issues in your solutions.

#### Enabled issues

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

**TODO: separate PyFinalInspection inside Hypertsyle??**
<details>
  <summary>PyFinalInspection</summary>

Reports invalid usages of final classes, methods and variables.

**ERROR-PRONE**
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

**ERROR-PRONE**, DISABLE
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

**ERROR-PRONE**
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

**BEST PRACTICE**
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

**ERROR-PRONE**
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

**ERROR-PRONE**
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

**BEST PRACTICE**
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

**ERROR-PRONE**
8. Example:
```python
from typing import final


@final
def foo():
    ...
```

Default description: `Non-method function could not be marked as '@final'`

**ERROR-PRONE**
9. Example:
```python
from typing import List, Final


def fun(x: Final[List[int]]) -> None:
    ...
```

Default description: `'Final' could not be used in annotations for function parameters`

**ERROR-PRONE**
10. Example:
```python
from typing import List, Final


def fun() -> Final[List[int]]:
    ...
```

Default description: `'Final' could not be used in annotation for a function return value`

**ERROR-PRONE**
11. Example:
```python
from typing_extensions import Final


class A:
    a: Final
```

Default description: `If assigned value is omitted, there should be an explicit type argument to 'Final'`

**ERROR-PRONE**
12. Example:
```python
from typing import Final


def fun():
    a: Final
```

Default description: `'Final' name should be initialized with a value`

**ERROR-PRONE**
13. Example:
```python
from typing import Final


class A:
    a: Final[int] = 1

    def __init__(self, a):
        self.a: Final[int] = a
```

Default description: `Already declared name could not be redefined as 'Final'`

**ERROR-PRONE**
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

**ERROR-PRONE**
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

**ERROR-PRONE**
16. Example:
```python
from typing import Final


class A:
    def foo(self):
        self.a: Final[int] = 5
```

Default description: `'Final' attribute should be declared in class body or '__init__'`

**ERROR-PRONE**
17. Example:
```python
from typing import Final


class Dummy:
    x: Final[int] = 1


class Demo(Dummy):
    x: str = ""

```

Default description: `''{0}'' is ''Final'' and could not be reassigned`

**ERROR-PRONE**
18. Example:
```python
from typing import Final, List


class A:
    a: List[Final] = 5
```

Default description: `'Final' could only be used as the outermost type`

**ERROR-PRONE**
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
  <summary>PyTupleItemAssignmentInspection</summary>

Reports assignments to a tuple item.

Example:
```python
t = ('red', 'blue', 'green', 'white')
t[3] = 'black'
```

Default description: `Tuples don't support item assignment`
</details>

#### Disabled issues

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
