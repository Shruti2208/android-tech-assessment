# Pelago Android Challenge

This is a skeleton project for our Android coding challenge, based around the following tech stack:

- Kotlin
- MVVM
- Jetpack Compose
- Coroutines
- Hilt
- Retrofit

The skeleton contains a set of mistakes with different severity which are placed across the whole codebase.
Feel free to change the project structure and code as you see fit, or add any dependencies you think are necessary to complete
the challenge.

## Challenge

Please, aim for 90–120 minutes; if you reach ~2 hours, stop and note remaining fixes. 
This is not a hard requirement, but we would be happy if you do not spend your whole weekend on it :)

Given the acceptance criteria below, you should identify the missing parts of the code and implement them. 
You also should find as much mistakes and errors as you can and fix them. If a fix would be too time‑consuming from your perspective, add inline comment near the relevant code explaining the issue and how you would address it (use // NOTE: or // FIX:).

While the feature itself is pretty simple, we encourage you to be creative and demonstrate your expertise when it comes
to architecture, user experience, overall best practices and attention to details.
We understand working on such a task can be time consuming so we do not expect you to provide an app with perfect UI 
or advanced gradle configuration. Tests are entirely optional.

## Acceptance criteria

### Scenario 1

**Given** I am on the home screen \
**When** The screen is first loaded \
**Then** A random fact is displayed

### Scenario 2

**Given** I am on the home screen \
**When** I press on the "More facts!" button \
**Then** A new random fact is displayed \
**And** The previously displayed fact is added to history only after the new fact has been successfully displayed

### Scenario 3

**Given** I am on the home screen \
**When** I press on the "Show history" button \
**Then** I am navigated to the history screen* \
**And** The list of the last 10 loaded facts is displayed (with newer facts replacing older ones)

*The latest fact should be on top of the list. History shows only previously loaded facts; the currently displayed home fact is excluded. If the new fact fails history remains unchanged.

### Scenario 4

**Given** I am on the history screen \
**When** I press on the "Back" button (use back gesture) \
**Then** I am navigated to the home screen\
**And** The previous loaded fact is displayed

## Resources

- [Random facts API Documentation](https://uselessfacts.jsph.pl/)

## Submission

You can either send us a link to a public repository (GitHub, Bitbucket, etc.) or a zip file with your project when
you're done.