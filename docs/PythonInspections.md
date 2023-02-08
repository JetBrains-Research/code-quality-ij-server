# The list of Python inspections

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