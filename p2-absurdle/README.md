Skills: Sets, Maps, Nested Collections, Documentation

Specification Sections:

- **Background** 🟩
- **A Game of Absurdle** 😠
  - Sample Execution 💻
- **Provided Scaffolding** 🏗️
  - Provided Constants ⬜
- **Assignment Requirements** 🧭
  - Creating Patterns for Guesses 🟨
  - Pruning the Dictionary 📖
  - Handling a User's Guess ❓
- **Development Strategy** ♟️
- **Implementation Details** ⌨️
- **Submission** 🏁
- **[Optional] Spec Quiz** 💯

## Background 🟩

### A Game of Absurdle 😠

Suppose the Absurdle manager only knows the following 4-letter words (the contents of the provided `small_dict.txt`).

- ally, beta, cool, deal, else, flew, good, hope, ibex

In Absurdle, instead of beginning by choosing a word, the manager narrows down its set of possible answers as the player makes guesses.

Turn 1

**Step A**: If the player guesses "argh" as the first word, the Absurdle manager considers all the possible patterns corresponding to the guess.

- ⬜⬜⬜⬜ — cool, else, flew, ibex
- ⬜⬜⬜🟨 — hope
- ⬜⬜🟨⬜ — good
- 🟨⬜⬜⬜ — beta, deal
- 🟩⬜⬜⬜ — ally

**Step B**: The manager picks the pattern that contains the largest number of target words (so that it can do as little pruning of the dictionary as possible). In this case, it would pick the pattern ⬜⬜⬜⬜ corresponding to the target words cool, else, flew, ibex.

Turn 2

**Step A**: If the player then guesses "beta", the manager chooses between the following possible patterns.

- ⬜⬜⬜⬜ — cool
- ⬜🟨⬜⬜ — else, flew
- 🟨🟨⬜⬜ — ibex

**Step B**: The manager would pick ⬜🟨⬜⬜ corresponding to the target words else, flew.

Turn 3

**Step A**: If the player then guesses "flew", the manager chooses between the following possible patterns.

- ⬜🟩🟨⬜ — else
- 🟩🟩🟩🟩 — flew

**Step B**: In this case, there's a tie between the possible patterns because both patterns include only 1 target word. The manager chooses the pattern ⬜🟩🟨⬜ not because it would prolong the game, but **because** ⬜🟩🟨⬜ **appears before** 🟩🟩🟩🟩 **when considering the patterns in sorted order**.

You don't need to worry about the criteria that makes one emoji come "alphabetically before" another. Write your code so that the patterns are considered in alphabetical order and you should be fine!

Turn 4

After this, there's only a single target word, else. The game ends when the player guesses the target word and the manager is left with no other option but to return the pattern 🟩🟩🟩🟩.

Watch the video below for an example playthrough of Absurdle!

[video](https://youtu.be/15KcwBCESGM)

#### Sample Execution 💻

You can see a sample execution of the game described above by clicking "Expand" below.

User input has been bolded and underlined for clarity!

```bash
Welcome to the game of Absurdle.
What dictionary would you like to use? small_dict.txt
What length word would you like to guess? 4
> argh
: ⬜⬜⬜⬜

> beta
: ⬜🟨⬜⬜

> flew
: ⬜🟩🟨⬜

> else
: 🟩🟩🟩🟩

Absurdle 4/∞

⬜⬜⬜⬜
⬜🟨⬜⬜
⬜🟩🟨⬜
🟩🟩🟩🟩
```

## Provided Scaffolding 🏗️

The final version of this program will be longer and more complicated than the programs we've asked you to implement up until now. Because of this, we are providing more scaffold code than we have in previous assignments. Our scaffold code will manage most of the file scanning, user input, and the functionality of the game continuing to prompt the user for guesses and ending when the user finally guesses the correct word.

### Provided Constants ⬜

Because emojis can be challenging to work with, the scaffold code includes class constants that you can use in your code rather than using the emoji itself!

```java
public static final String GREEN = "🟩";
public static final String YELLOW = "🟨";
public static final String GRAY = "⬜";
```

### Assignment Requirements 🧭

Creating Patterns for Guesses 🟨

```java
public static String patternFor(String word, String guess)
```

As the user makes guesses, the game will need to produce a pattern for the given guess (made up of the 🟨🟩⬜ blocks) as described above. You should write a helper method called patternFor that will be used to generate the pattern for a given target word and guess.

Below are some example calls and their produced pattern:

- `patternFor("abbey", "bebop")` — 🟨🟨🟩⬜⬜. Notice how the middle letter 'b' in the guess "bebop" is green, while the first letter 'b' is yellow. Green tiles are assigned before yellow tiles.
- `patternFor("abbey", "ether")` — ⬜⬜⬜🟩⬜. Notice how the second 'e' in the guess "ether" is green, while the first 'e' is gray, not yellow. The 'e' in "abbey" is used up when it forms a green match with the second 'e' in "ether" and cannot be reused for a yellow match later. Any letters in a green match are 'used up' before yellow matches are considered.
- `patternFor("abbey", "keeps")` — ⬜🟨⬜⬜⬜. Notice how only the first letter 'e' in the guess "keeps" is yellow, while the second letter 'e' is gray. If there are multiple places for a yellow tile, choose the leftmost place.
- `patternFor("bebop", "abbey")` — ⬜🟨🟩🟨⬜. Notice that the pattern for this method call is different than the one above, though the same words are used. This is because it matters which String is considered the "target word" and which is considered the "guess."

There are two approaches you could follow to implement this behavior:

- **Algorithm 1: List and Map Approach**
  - Convert the String guess into a `List<String>` of each individual character in the guess. Over the course of the algorithm, we will replace each element with colored squares to generate the guess's pattern.
  - Generate a `Map<Character, Integer>` which counts the characters from word which haven’t yet been used in an exact or approximate match.
  - Mark all exact matches by updating the corresponding letters in the `List<String>` guess to be a `GREEN` square, and then updating the map of character counts to reflect that those characters have been used
  - Mark all approximate matches by updating letters in the `List<String>` to be a `YELLOW` square if there is still an unused matching character from the word, and then updating the map of character counts to reflect that those characters have been used
  - Replace all unmarked characters in the `List<String>` with `GRAY`
  - Concatenate the elements of the `List<String>` back into one String to be returned
- **Algorithm 2: Special Markers String Approach**
- Mark all exact matches in the guess and the target word by replacing the matching characters with `!`.
- Mark all approximate matches in the guess and the target word by replacing any characters that appear in both the guess word and the target word with `$`.
- Mark all non-matches by replacing any remaining characters (that are not `!` or `$`) with `%`.
- Replace the `!`, `$`, and `%` marker characters with their respective colored tiles.

The "patternFor Development" slide goes into more detail about each implementation and allows you to test your `patternFor` method in isolation, so we strongly recommend that you complete the development slide before moving on to the rest of the program. While you're free to implement your own algorithm rather than following one of ours, **you may use at most 2 data structures: 1 `Map` and 1 additional data structure of your choice** (but you are welcome to use fewer than 2 if you wish).

### Preparing the Dictionary 📖

```java
public static Set<String> prepDictionary(List<String> dictionary, int wordLength)
```

The provided scaffold code will handle asking the user for the length of the word they would like to guess, asking the user for the dictionary file to be used, and reading the words from the provided dictionary into a `List<String>`. The scaffold code will then pass the `List<String>` of the dictionary's words and the user's chosen word length into `prepDictionary`.

For example, if the example dictionary file from above was used, `prepDictionary` would be passed a list that would contain

```java
[ally, beta, cool, deal, else, flew, good, hope, ibex]
```

You should write a method `prepDictionary` that takes the `List<String>` containing the contents of the dictionary file as a parameter and the user's chosen word length, and return a `Set<String>` that contains only the words from the dictionary that match the user's chosen word length. You may assume that the given dictionary contains only non-empty `String` composed entirely of lowercase letters. However, you should not make any similar assumptions about the given `wordLength`: if `wordLength` is less than 1, this method should throw an **`IllegalArgumentException`**.

**Your method should not modify the given dictionary—it should remain unchanged after your method has finished executing!**

### Handling a User's Guess ❓

```java
public static String recordGuess(String guess, Set<String> words, int wordLength)
```

Our provided scaffold code will handle repeatedly asking the user for a guess, but you will need to write the code to record, or "process", the user's guess. Using the given guess, your method should update the set of words that are now under consideration, and return the pattern for the guess.

Recall the description of Absurdle (as opposed to its more benevolent counterpart, Wordle):

Absurdle gives the impression of picking a single secret word, but instead what it actually does is consider the entire list of all possible secret words which conform to your guesses so far. Each time you guess, Absurdle prunes its internal list as little as possible, attempting to intentionally prolong the game as much as possible.

To that end, this method should:

1. Consider the possible patterns given the user's guess and the possible answers it still has left to choose from. If the set of words is empty or the user's guess does not have the correct length, you should throw an **`IllegalArgumentException`**.
2. Choose the "best" pattern, which we define to be the one that corresponds to the largest set of words still remaining. (In other words, choose the pattern that results in as little pruning of the dictionary as possible.) In case of a tie, choose the pattern that comes first alphabetically.
3. Modify the passed-in set of words to contain only the words matching the "best" pattern.

To implement this behavior in `recordGuess`, you will want to construct a `Map` that associates **patterns** with **target word groups** and use it to pick the pattern associated with the largest number of target words. These word buckets should be unordered groupings of unique words and should prioritize efficiency. When there are multiple patterns that have the same largest number of target words, pick the pattern that appears first in the sorted order. The associated target word set becomes the dictionary for the next call to `recordGuess`.

**You should use a nested collection to achieve this!** Given that your program will need to consider patterns in alphabetical order, think carefully about what implementation of Map and Set are most appropriate for this problem.

Since `recordGuess` is a complicated algorithm composed of many significant tasks, we want you to practice good functional decomposition by including **at least one helper method for recordGuess outside of `patternFor`!**

Development Strategy ♟️

As in previous assignments, we strongly encourage you to write and test your code in stages rather than attempting to write it all at once before trying to run or test your work. You may recall that this is referred to as "iterative enhancement" or "stepwise refinement."

We suggest that you work on your assessment in three stages. See the sections above for more information about what each method entails.

- **Part 1** - Write the patternFor method. The following slide (named "patternFor Development") should help you write your patternFor method in isolation before moving on to the rest of the program. Once you're passing all tests in the patternFor slide, copy and paste your completed method into the "Absurdle" slide to include it in your full program.
- **Part 2** - Write the prepDictionary method. This is called by the scaffold code at the very beginning of the program, so it will be helpful to have it in place before you move on to actually accepting and recording the user's guesses.
- **Part 3** - Write the recordGuess method. This will involve calling the patternFor method, but you should call at least one other method from recordGuess that you implement in addition.

## Submission 🏁

When you are ready to submit, go to the "🏁 Final Submission 🏁" slide, read the statement and sign your name. Finally, click "Submit" in the upper-right corner. You may submit as many times as you want until the due date.

You can see your previous submissions by clicking the three dots icon in the upper-right and selecting "Submissions and Grades." By default, we will grade your latest submission from before the deadline. However, if you would like us to grade a different submission, you can select that submission on the left side of the window and click "Set final." Note that we will not grade any submission made after the deadline-- if you mark a submission after the deadline as final, we will grade your most-recent on-time submission instead.

Please make sure you are familiar with the resources and policies outlined in the syllabus and the programming assignments page.

## [Optional] Spec Quiz 💯

The questions below are short questions asking about the algorithm used in Absurdle. They are not graded, but are provided here so that you can make sure fully understand what we're asking you to implement before starting on the assignment!

Complete the spec quiz and check your answers with the explanations in the expand below!

Question 1:  1

1! You will need to write one helper method for specifically recordGuess. According to the spec:

Since recordGuess is a complicated algorithm composed of many significant tasks, we want you to practice good functional decomposition by including at least one helper method for recordGuess outside of patternFor!
Question 2:  C

Remember, once the target words have been grouped by pattern produced by comparing the guess against each word, it chooses to move forward with the pattern that fits the largest number of target words. This way it can prune the dictionary of words as little as possible and still have lots of options to keep the user guessing.

Question 3:  E

Remember that your program should consider each pattern in alphabetical order. If there is a tie for which pattern has the largest number of target words associated with it, your program should choose the one that comes alphabetically first.

Question 4:  B, E, F

If the dictionary contains 3 words, there can only be at most 3 unique patterns. In larger dictionaries, we'd expect that many words would share the same pattern, but in this tiny example, each word in the dictionary has a different pattern.

⬜⬜⬜⬜ — all three of dogs, cats, bird share at least one letter with the guess
⬜⬜⬜🟨 — patternFor("cats", "dirt")
⬜⬜🟨⬜ — wrong pattern for cats; should assign yellow tiles to the guess
⬜🟩🟩🟨 — wrong pattern for bird; should assign yellow tiles to the guess
🟨🟩🟩⬜ — patternFor("bird", "dirt")
🟩⬜⬜⬜ — patternFor("dogs", "dirt")
Remember that the tiles are assigned to the guess, not the target word.

### Question 1

At least how many helper methods do you need to write for your program?

### Question 2

In the example with 4-letter words, we described what happens when the manager has the target words cool, else, flew, ibex.

When the player guesses "beta", the manager chooses between the following possible patterns.

- ⬜⬜⬜⬜ — cool
- ⬜🟨⬜⬜ — else, flew
- 🟨🟨⬜⬜ — ibex

Why did the manager choose the pattern ⬜🟨⬜⬜?

- [ ] The pattern contains the most ⬜ gray square tiles.
- [ ] The pattern contains the least 🟨 yellow square tiles.
- [ ] The pattern correspond to the largest number of target words.
- [ ] The pattern corresponds to the smallest number of target words.
- [ ] The pattern appears earlier in sorted order.
- [ ] The pattern appears later in sorted order.

### Question 3

After the next guess, we described what happens when the manager has the target words else, flew.

When the player guesses "flew", the manager chooses between the following possible patterns.

- ⬜🟩🟨⬜ — else
- 🟩🟩🟩🟩 — flew

Why does the manager choose the pattern ⬜🟩🟨⬜ instead of 🟩🟩🟩🟩?

- [ ] The pattern contains the most ⬜ gray square tiles.
- [ ] The pattern contains the least 🟨 yellow square tiles.
- [ ] The pattern correspond to the largest number of target words.
- [ ] The pattern corresponds to the smallest number of target words.
- [ ] The pattern appears earlier in sorted order.
- [ ] The pattern appears later in sorted order.

### Question 4

Suppose we started a new game of Absurdle with the possible 4-letter target words dogs, cats, bird.

If the player guesses "dirt", what patterns will be generated in the recordGuess method?

- [ ] ⬜⬜⬜⬜
- [ ] ⬜⬜⬜🟨
- [ ] ⬜⬜🟨⬜
- [ ] ⬜🟩🟩🟨
- [ ] 🟨🟩🟩⬜
- [ ] 🟩⬜⬜⬜
