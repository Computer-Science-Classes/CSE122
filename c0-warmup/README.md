# Full Specification 🎵

This assignment is intended to warm up coding in CSE 122 with the Java programming language. Depending on your programming experience, this assignment might feel a little different for you.

- *For students who took CSE 121*: Welcome! 🎉 This assignment should review the concepts and programming skills you learned in CSE 121.
- *For everyone else*: Welcome! 🎉 This assignment should review the fundamental concepts of programming (variables, looping, conditionals, methods/functions, arrays) that you should be familiar with from your prior programming experience. If you are coming from a programming language other than Java, this assignment will allow you to practice these familiar programming concepts in Java. See the Java Tutorial for examples of these fundamental coding constructs in Java.
- *For everyone*: Please read our code quality and commenting guide before completing this assignment!

**Skills**: Loops, Scanners, Conditionals, Strings, Methods, Parameters, Returns, Arrays, 2D Arrays, Commenting, Functional Decomposition

- Background 🎼
  - Simplifications 🥁
- Musical Representation 🎹
- Constants 🏗️
- Assignment Breakdown 🧱
- Basic Task: `composeSong` ✍️
  - Functional Decomposition 📓
  - Implementation Tips 💡
- Creative Extension ❗
  - `Functional Decomposition` 📓
  - `mostCommonNaturals` 🏆
  - `findChord` 🔎
- MusicBox Notes Player 🎼
- Feeling Stuck on this Assignment❓
- Submission 🏁

## Background 🎼

### Musical Representation 🎹

In the context of this assignment, melody is defined as sequences of pitches.

In this problem, we will think of our representation of a melody as a `String[]` where each element corresponds to a pitch. As stated in the **Background** 🎼 section of the specification, a pitch is labeled by one of the letters C, D, E, F, G, A, or B, and an optional sharp `♯` or flat `♭` symbol. As an illustration, a simple melody might be coded as `{"A", "C♭", "D", "C♯"}`, which denotes a sequence of notes including A, C flat, D, and C sharp. Another example of a classic melody, "Hot Cross Buns" in C Major, could be represented as `{"E", "D", "C", "E", "D", "C", "C", "C", "C", "C", "D", "D", "D", "D", "E", "D", "C"}`.

**A complete song**, which may consist of multiple melodies played together all at once, can be represented as a 2D array of `String`s `(String[][])`. Each element in the outer array represents **one melody**. Each element in an inner array represents **one pitch**.

As an example, here is a song comprised of 3 different melodies, each containing 2 pitches or notes:

```java
String[][] song = {
    {"C", "C♭"},    // 1st melody with pitches C and C♭
    {"E", "E♭"},    // 2nd melody with pitches E and E♭
    {"A", "G♯"}.    // 3rd melody with pitches A and G♯
};
```

For the sake of simplicity in this assignment, **we will assume that all melodies of a song are the same length!**

### Constants 🏗️

We have given you the following class constants to help you develop your program. It is not required that you use these but you may find them helpful, especially when debugging and developing your Creative Extension.

```java
public static final String NOTES = "CDEFGAB";
public static final String SHARP = "♯";
public static final String FLAT = "♭";
```

**When running your program, you may need to type in a sharp `♯` or flat `♭` symbol. You can copy paste the sharp or flat symbol from any part of the specification, including these class constants.**

## Assignment Breakdown 🧱

**Passing all of the autograder tests is necessary, but not sufficient, to achieve an E on this assignment.** On other words, you cannot achieve an E without passing the autograder tests. However, even if you are passing all of the test cases, you are not guaranteed an E for the assignment if the entirety of the requirements are not met.

This assignment's coding portion has two main parts:

- **The `composeSong` Method**: Write a method that allows users to input a series of notes, ultimately piecing together a complete song.
- **Creative Extension**: Choose and implement one of two options: `mostCommonNaturals` or `findChord`.

We have provided the entirety of `main` in the scaffold, so you do not need to modify any of it yourself. Specifically for this assignment, you should have a total of 6 or more methods (including main) in your `MusicBox` class:

- a main method (we've provided this for you already!)
- the `composeSong` method
  - along with **at least one helper method** for `composeSong` using **functional decomposition**
- the chosen creative extension method (either `mostCommonNaturals` or `findChord`)
  - along with **at least two helper methods** for the chosen extension using **functional decomposition**

Note that when you are being asked to write helper methods, that means these methods are in addition to the original method. For example for `composeSong`, your program should consist of at least two methods — the `composeSong` method and one helper method in addition to `composeSong`.

You should complete the `composeSong` method **before** attempting the Creative Extension! We strongly recommend taking advantage of the `composeSong` development slide and starting from there :)

### `composeSong` ✍️

We have provided you with a runnable Java program called `MusicBox.java` with a fully completed main method. In other words, you do not need to modify the `main` method as we have done that for you already!

In the `MusicBox` class, the main method calls the `composeSong` method. Your task is to implement the `composeSong` method that should:

- Accept a `Scanner` object as a parameter to read user input.
- Prompt the user to specify the number of melodies for their song.
- Prompt the user to specify the number of notes in each melody.
- For each melody they wish to compose, prompt each note one at a time.
- Return a `String[][]` representing the user's song, where each inner array represents a melody within the user-composed song.

The following text shows one example log of the program's output. The bold underlined text shows the user input and does not need to be printed by your program as that is what the user will type into the computer.

```java
Enter the number of melodies: 3
Enter the length of each melody: 2

Composing melody #1
  Enter note #1: C
  Enter note #2: C♭

Composing melody #2
  Enter note #1: E
  Enter note #2: E♭

Composing melody #3
  Enter note #1: A
  Enter note #2: G♯

Returned song 2D array:
C C♭ 
E E♭ 
A G♯ 
```

The `composeSong` method will return the following 2D array based on the user input above:

```java
String[][] song = {
    {"C", "C♭"},
    {"E", "E♭"},
    {"A", "G♯"}
};
```

### Functional Decomposition 📓

You should use **functional decomposition** to break down `composeSong` into at least **one** additional helper method to solve non-trivial sub-problems of the task. See the in-class example "That's a Bingo!" this week on how to decompose larger problems into smaller ones.

- Hint: Think of how you would describe your algorithm (i.e. the sequence of steps needed) to solve your problem. If you find yourself saying, "To solve this, I need to do A **and then** B", A and B might be good candidates for helper methods!
- Essentially, you will have to write one helper method for `composeSong` so your program in total, should consist of at least 2 methods for `composeSong`.

### Implementation Tips 💡

- The user **will always type in integer numbers and valid notes** (so you do not need to handle any invalid input!). Zero is considered a valid number for this program.
- We recommend that to read numerical input, use `scanner.nextLine()` followed by `Integer.parseInt(input)` to convert the `String` to an `int`. This means you should not read integer input with `.nextInt()` if you use `scanner.nextLine()`. Instead, when prompting for integers, read user input as a `String` with `.nextLine()` and convert it to an int in the following way:

```java
String input = console.nextLine();
int num = Integer.parseInt(input);
```

- When running your program, you may need to type in a sharp `♯` or flat `♭` symbol. You can copy/paste the sharp or flat symbol from any part of the specification.

## Creative Extension❗

Once you've implemented the `composeSong` method, it's time to enhance your program with an additional feature! **Choose one** of the following creative options to expand your `MusicBox` class.

Note that there is no **expected output** for the creative extension so you **should not print anything out in the creative option** that you choose or modify the main method in your final submission!

You should use **functional decomposition** to break the option you choose into at **least two additional helper methods** to solve non-trivial sub-problems of the task. See the in-class example "That's a Bingo!" this week on how to decompose larger problems into smaller ones.

- Note that you are being asked to write two helper methods in addition to `mostCommonNaturals` or `findChord` so the Creative Extension portion of your program should consist of at least 3 methods in total.

You may also want to refer back to the **Constants** 🏗️ section of the spec if you chose not to use the suggested class constants while implementing `composeSong`.

### Option 1: `mostCommonNaturals` 🎵 (recommended)

Quick Background: A musical note is natural if it is **not** sharp (♯) or flat (♭).

`mostCommonNaturals(String[][] song)`

Write a method called `mostCommonNaturals` that takes a `String[][]` song as a parameter and returns the natural musical note that appears most frequently in each of the melodies in that song as a `String[]`. Here are some examples of how this method may be called and what it should return.

#### Example 🖨️

- `mostCommonNaturals({{"A", "C", "D", "C"}})` should return `{"C"}`.
  - Here, one melody is passed into the method.
- `mostCommonNaturals({{"A♯", "C♯", "D", "C♭", C♯}})` should return `{"D"}`.
  - Note that even though C♯ appears twice and D appears once, C♯ is not a natural note so it should be ignored.
- `mostCommonNaturals({{"C", "C", "D"}, {"E", "E", "G♭"}, {"A", "G♯", "A"}})` should return `{"C", "E", "A"}`.
  - Here, three melodies are passed into the method.
- `mostCommonNaturals({{"A", "B", "C"}, {"C♯", "C", "A"}})` should return `{"C", "C"}` (see the next paragraph for an explanation of why).

In the case that two notes are tied for appearing the most frequently, you should choose the natural note that comes first in the order of C, D, E, F, G, A, B. So, for example, if there is a tie between B and C, you should choose C because it appears before B in the sequence C, D, E, F, G, A, B.

You may make the following assumptions about the input:

- The provided `String[][]` song is non-empty
- Each `String[]` melody is non-empty
- Each `String[]` melody contains only uppercase letters C, D, E, F, G, A, or B - each with the possibility of also being sharp (♯) or flat (♭)
- Each `String[]` melody contains at least one natural note.

Your solution should use an `int` array of length 7 to represent the counts of the notes C, D, E, F, G, A, and B. Note that you will likely want to choose which letter belongs in which index carefully to handle the tie-breaking case. Hint: It is okay to use if/else statements to decide how a letter gets put into a certain index, but there are also other ways to solve this task (take another look at the `NOTES` class constant).

##### Option 2: `findChord` 🔎

Quick Background: Chords are groups of musical notes that sound pleasant when played together. Like a melody, we can represent each chord as a `String[]` of notes.

`findChord(String[][] song, String[] chord)`

Your task is to write a method called findChord that takes a `String[][]` song and a `String[]` chord as parameters. Your method should return an int representing the first column of the given song within which the given chord is present. The column should contain all the notes and only the notes in the given chord. If a song does not contain the given chord then your method should return `-1`.

Consider the following song and chord:

```java
String[][] song = {{"E", "A", "A", "A"},
                   {"F", "A", "B", "C♯"}, 
                   {"F", "B", "C♯", "B"},
                   {"F", "B", "D", "C♯"}};

String[] chord = {"A", "B", "C♯"};
```

The first notes from each melody are E, F, F, F. We can see that this column does not contain any notes from our given chord (A, B, C♯). So we move on to the next column.

The second notes from each melody are A, A, B, B. We can see that this column contains the A and B from the given chord but is missing the C♯. So we move on to the next column.

The third notes from each melody are A, B, C♯, D. This column contains all the notes from the given chord. However, the column also contains an extra note D that does not appear in the given chord. So we move on to the next column.

Finally, the fourth column contains the notes A, C#, B, C#. This column contains all the notes from the given cord and no additional notes not present in the cord so we return 3 (remember zero-based indexing).

To summarize, our suggested algorithm for this extension is as follows:

1. Traverse the `song` 2D array column by column.
    1. For each column, check if the column contains all the elements in the given `chord` array  
    2. If the column contains all the notes in the given `chord` array, now check to see if the `chord` array contains all the notes in the column.
2. If yes, return the index of the column.
3. If no column contains all and only the notes in the given chord, return `-1`.

You may make the following assumptions about the input:

- The provided `String[][]` song is non-empty
- Each `String[]` melody is the same length
- Each `String[]` melody and `String[]` chord contains only uppercase letters C, D, E, F, G, A, and B - each with the possibility of also being sharp (♯) or flat (♭)
- If the melody is shorter than the given chord, you may assume that the chord has no duplicate notes. In other words, none of the columns in the song would have the chord since the melodies will never have enough notes.

You will likely want to be extra careful when traversing your `song` 2D array. We highly recommend traversing your `song` 2D array column by column rather than row by row.

### MusicBox Notes Player 🎼

The MusicBox Notes Player is solely for the Reflection portion of this assignment and **you do not** need to write any code for this!

You can directly copy and paste the returned song output of your `composeSong` method into the MusicBox Notes Player and click the "Play Song" button. Feel free to get creative! Once you're happy with it, just copy and paste your output for your song into the reflection :)

## Feeling Stuck on this Assignment❓

It's completely understandable if you find this assignment a bit challenging! Especially if you don't have a lot of experience programming in Java. Remember that learning is a challenging process, and you don't have to do it alone!

- You can visit the Introductory Programming Lab (IPL) to talk with a TA about programming concepts or get help on assignments.
- You can post questions on the discussion board here on Ed! You can make questions public (anyone can see them) or private (only course staff can see them). This is a great way to asynchronously get help on an assignment or ask questions about the course.
- You can stop by the instructor's office hours to discuss course concepts, get help on assignments, or discuss the course in general.
See the 122 Course Website Office Hours page for more info!

Submission 🏁

When you are ready to submit, go to the "🏁 Final Submission 🏁" slide, read the statement and sign your name. Finally, click "Submit" in the upper-right corner. You may submit as many times as you want until the due date.

You can see your previous submissions by clicking the three dots icon in the upper-right and selecting "Submissions and Grades." By default, we will grade your latest submission before the deadline. However, if you would like us to grade a different submission, you can select that submission on the left side of the window and click "Set final." Note that we will not grade any submission made after the deadline! If you mark a submission after the deadline as final, we will grade your most recent on-time submission instead.

Make sure you are familiar with the resources and policies outlined in the syllabus and the creative projects page!
