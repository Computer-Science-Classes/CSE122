**Skills**: Interfaces, Objects, Data Structures, Documentation

Specification Sections:

- Background 🔎
- Program Behavior 👩‍💻
- Error Class ❌
- Checker Classes ☑️
  - LongLineCheck
  - BreakCheck
  - BlankPrintlnCheck
- Linter Class 📝
- Development Strategy ♟️
- Assignment Requirements 📋
- Reflection Proposal 🎨
- Submission 🏁
- [Optional] Spec Quiz 💯

## Background 🔎

Note: You do not need to read this section to complete the assessment, but it provides some helpful context that may make the assessment easier to understand.

### What is Linting?

We've all encountered bugs when writing code, and explored strategies to debug these errors. Compilers help greatly with syntax errors, and other tools like debuggers help us find functional bugs. However, these tools can't help us with code quality; that's where linting comes in. 

**Linters** are programs that involve reading over other programs to highlight formatting or some other violations based on some **opinionated set of style guidelines** that define how programs should be written. 

Some examples are programs called checkstyle for linting Java programs and flake8 for linting Python programs.

Stephen C. Johnson, a computer scientist at Bell Labs coined the term linting in 1978 while dealing with code portability issues 

The term "lint" was derived from lint, the name for the tiny bits of fiber and fluff shed by clothing, as the command should act like the lint trap in a clothes dryer, detecting small errors to great effect.

### Why does linting matter?

Writing Java code isn't just about writing code that compiles and works. If we wanted code that just got a computer to follow our directions, why not just write it in binary? Truth is, code isn't written for computers; it's written for people. 

Programming in the real world is a highly collaborative activity, so it's very important to be able to write code that is easy to work with and understand. Having clean, readable code also makes finding bugs and errors in your code significantly easier. There are numerous code quality guides that define their own set of rules that programmers should follow. 

Linters help us simplify this process and enforces a set of style guidelines by highlighting instances of incorrect formatting or other violations. 

### Who decides the style guidelines?

There isn't "one true style guide" that all programmers follow! Style guides are extremely customizable and rules can be switched in and out easily. These guidelines are somewhat arbitrary (decided on by people based on preference), but organizations use them up to ensure consistency among their codebase. 

This assignment will present some rules for you to implement, but these rules are not all-inclusive! You all can come up with other rules that might be important to you as a programmer 😃 

## Program Behavior 👩‍💻

In this assignment, you will implement a **program linter** that analyzes Java files. This linter will be driven by multiple classes that work in tandem to detect errors and present them to the user. 

Class diagram of how the "Linter" class runs three checks- BreakCheck, LongLineCheck, and BlankPrintlnCheck. Each of these checks can produce an Error and implement the Check interface. This shows that each class implements the same interface and are executed by the Linter to analyze code line by line.
As you read through the descriptions of each class below, you may find it helpful to refer back to the diagram above to understand how these different classes work together.

The program is driven by a class Linter that lints a file given a list of checks. You will need to iterate through files line by line and run checks on each line of the Java file. 

Like we mentioned in the background section, these checks are completely customizable, and we've come up with 3 such checks: BreakCheck, LongLineCheck, and BlankPrintlnCheck. Each check can give us an Error. To define these checks in Java, we've defined an interface called Check that these checks should implement. 

Error Class ❌

You will implement the methods and constructor defined in Error.java to represent some key properties and operations of an error.

Error.java

public Error(int code, int lineNumber, String message) 

Constructs an Error given the error code, line number, and a message.

public String toString() 

Returns a String representation of the error with the line number, error code and message. The representation should be formatted as follows (replace curly braces with the actual values):

(Line: {lineNumber}) has error code {code}: {message}
For instance, an error on line 122 with error code 5 might look like this:

(Line: 122) has error code 5: Some error message!
public int getLineNumber()

Returns the line number on which this error occurred.

public int getCode()

Returns the error code of this error.

public String getMessage()

Returns the message for this error.

Check Classes ☑️

You will have to implement specific checks to highlight errors found in the source files. We provided an interface Check.java that defines a single method public Error lint(String line, int lineNumber) . All the checkers you write should implement this interface and hence, you need to implement the lint method in each checker.

For this assignment, you'll implement the following checks:

LongLineCheck
Has error code 1
Should return an error (with a custom message) if the given line length is greater than 100 characters; otherwise, it should return null.
BreakCheck
Has error code 2
Should return an error (with custom message) if the given line contains the break keyword outside of a single line comment (comments that start with //); otherwise, it should return null. Note that:
Your check should only look for break specifically in all-lowercase (so occurrences of Break or BReaK should never be flagged).
In the case that there is a break statement before an inline comment, that line should still be flagged (e.g. break // inline comment).
This check is overly-simplistic in that it might flag some false uses of break such as System.out.println("break");, but you do not need to handle this case specially; you should flag any use of the keyword break outside of a single line comment.
This check class should still flag break inside any multi-line comments (e.g. /* break */).
You may find the substring and indexOf methods of the String class useful!
BlankPrintlnCheck
Has error code 3
Should return an error (with custom message) if the given line contains the pattern System.out.println(""). Otherwise, it should return null. Note that:
There is no semicolon ; included in the pattern!
In your solution, you will likely need to use an escape sequence which are specific sequences of characters in Java to represent special characters in Strings. In particular, you should use \" to insert a double-quote " in a String.
Linter Class 📝

Finally, in Linter.java, you will implement the constructor and key method of the linter that runs Checks on a file.

Linter.java 

public Linter(List<Check> checks)

Constructs a Linter given a list of checks to run through.

public List<Error> lint(String fileName) throws FileNotFoundException

Lints the given files line-by-line by running through all the checks passed into the constructor. You should first open the file (specified by fileName) and read through the file line-by-line; you can use the File and Scanner classes to help accomplish this. For each line, run through all the checks that were supplied to this Linter upon construction. Returns a list of all non-null errors found.

Development Strategy ♟️ 

We recommend developing classes in the following order:

Error
The classes that implement the Check interface
LongLineCheck
BlankPrintlnCheck
BreakCheck
Linter
You will likely be able to debug your work more easily if you test each class individually. Note that you should be editing LinterMain to test all of your Checks. Before submitting, make sure your LinterMain is testing all of your Checks!

As you work on your code, be sure to think carefully about the state (i.e. the fields) necessary for each class. It will be difficult or impossible to achieve the correct behavior if you are not storing the correct state in your objects. In particular, you will likely need to use fields to "remember" values passed to constructors to ensure they are available for later use.

Reflection Proposal 🎨

There are only three checks in our linter at the moment, and to come up with more we have decided to crowdsource! Along with your program, part of the reflection will ask you for a proposal for a new check to add to the linter. Your proposal must include the following elements:

The name of the check
The error code of the check
A description of what the check does
You should format your proposal like the example below. 

You do not need to provide an implementation for your custom check.

Sample response

A proposal for the BreakCheck might look as follows:


Submission 🏁 

When you are ready to submit, go to the "🏁 Final Submission 🏁" slide, read the statement and sign your name. Finally, click "Submit" in the upper-right corner. You may submit as many times as you want until the due date. 

You can see your previous submissions by clicking the three dots icon in the upper-right and selecting "Submissions and Grades." By default, we will grade your latest submission from before the deadline. However, if you would like us to grade a different submission, you can select that submission on the left side of the window and click "Set final." Note that we will not grade any submission made after the deadline-- if you mark a submission after the deadline as final, we will grade your most-recent on-time submission instead.

Please make sure you are familiar with the resources and policies outlined in the syllabus and the programming assignments page.

[Optional] Spec Quiz 💯 

The questions below are short questions asking about details from the spec above. They are not graded, but are provided here so that you can make sure fully understand what we're asking you to implement before starting on the assignment! 
