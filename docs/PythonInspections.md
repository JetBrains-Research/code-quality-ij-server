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
takes variables declared by them and ensures none of parent `with or `for` declares variable with the same name.

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
  <summary></summary>

Example:
```python

```

Default description: ``
</details>

- PyCallingNonCallableInspection
- PyComparisonWithNoneInspection
- PyDataclassInspection
- PyDictCreationInspection
- PyDictDuplicateKeysInspection
- PyDunderSlotsInspection
- PyExceptClausesOrderInspection
- PyFinalInspection
- PyClassVarInspection
- PyFromFutureImportInspection
- PyGlobalUndefinedInspection
- PyInconsistentIndentationInspection
- PyIncorrectDocstringInspection
- PyMissingOrEmptyDocstringInspection
- PyNamedTupleInspection
- PyArgumentListInspection
- PyRedeclarationInspection
- PyMethodParametersInspection
- PyUnreachableCodeInspection
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