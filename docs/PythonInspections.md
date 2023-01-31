# The list of Python inspections

TODO: add descriptions and examples

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

**TODO: add example**

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

**TODO: add example**

11) `Mutable default ''{0}'' is not allowed. Use ''default_factory''`

```python
from dataclasses import dataclass


@dataclass
class A:
    bar: list = []
```

12) `A default is set using ''{0}''`

**TODO: add example**

13) `''{0}'' should take only {1} {1,choice,1#parameter|2#parameters}`

**TODO: add example**

14) `Attribute ''{0}'' lacks a type annotation`

**TODO: add example**

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

**TODO: add example**

21) `''{0}'' method should be called on dataclass instances or types`, `''{0}'' method should be called on dataclass instances`, `''{0}'' method should be called on attrs instances`, `''{0}'' method should be called on attrs types`

**TODO: add example**
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

```

Default description: `'@final' should be placed on the first overload` (only for stubs)

**TODO: add an example**, see - https://peps.python.org/pep-0591/

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

```

**TODO: add an example**

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

```

**TODO: add example**

Default description: `Possible callees`

4. Example:
```python

```

**TODO: add example**

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

```

**TODO: add example**

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
  <summary></summary>

Example:
```python

```

Default description: ``
</details>

- PyMethodFirstArgAssignmentInspection
- PyStringFormatInspection
- PyMethodOverridingInspection
- PyInitNewSignatureInspection
- PyTrailingSemicolonInspection
- PyReturnFromInitInspection
- PyTupleAssignmentBalanceInspection
- PyClassicStyleClassInspection
- PyExceptionInheritInspection
- PyUnboundLocalVariableInspection
- PyStatementEffectInspection
- PySuperArgumentsInspection
- PyNonAsciiCharInspection
- PyTupleItemAssignmentInspection
- PyPropertyAccessInspection
- PyPropertyDefinitionInspection
- PyNestedDecoratorsInspection
- PyOldStyleClassesInspection
- PyCompatibilityInspection
- PyListCreationInspection
- PyUnnecessaryBackslashInspection
- PySingleQuotedDocstringInspection
- PyMissingConstructorInspection
- PySetFunctionToLiteralInspection
- PyDecoratorInspection
- PyTypeCheckerInspection
- PyDeprecationInspection
- PyMandatoryEncodingInspection
- PyClassHasNoInitInspection
- PyNoneFunctionAssignmentInspection
- PyProtectedMemberInspection
- PyMethodMayBeStaticInspection
- PyDocstringTypesInspection
- PyShadowingNamesInspection
- PyAbstractClassInspection
- PyMissingTypeHintsInspection
- PyOverloadsInspection
- PyProtocolInspection
- PyTypeHintsInspection
- PyTypedDictInspection
- PyChainedComparisonsInspection
- PyPep8NamingInspection
- PyShadowingBuiltinsInspection
- PyUnresolvedReferencesInspection
- PyTestParametrizedInspection
- PyPep8Inspection
- PyInterpreterInspection
- PyStubPackagesCompatibilityInspection
- PyStubPackagesAdvertiser
- PyRelativeImportInspection