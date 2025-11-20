**Pelago Android Challenge**

Welcome to the Pelago Android challenge! Glad to see you here.

Below you will find all the information required for the assessment.

We don’t want you to spend your entire weekend on the challenge - we recommend no more than 90-120 minutes. If you reach ~2 hours, please stop and note remaining fixes as per the instructions below.

This is a skeleton project for our Android coding challenge, based around the following tech stack:

- Kotlin
- MVVM
- Jetpack Compose
- Coroutines
- Hilt
- Retrofit

The skeleton contains a set of mistakes of different severity levels, which are placed throughout the codebase.

**Instructions:**

The use of AI agents is permitted and even encouraged. If you opt-in to use, please let us know which tools and agent(s) you used with the submission.

1. Fork the project
2. Apply your changes
3. Share the project with us via GitHub or send us a `.zip`

**The Challenge**

1. Given the acceptance criteria below, identify the missing criteria and implement them.
2. Review the existing code and modify/fix the mistakes and errors. If a fix would be too time-consuming from your perspective, add inline comment near the relevant code explaining the issue and how you would address it (use `// NOTE:`, `// FIX:`, or `// TODO:`).

While the feature itself is pretty simple, we encourage you to be creative and demonstrate your expertise when it comes to architecture, user experience, overall best practices and attention to detail. We understand working on such a task can be time consuming so we do not expect you to provide an app with perfect UI or advanced gradle configuration. Tests are optional.

**Acceptance criteria**

**Scenario 1**

**Given** I am on the home screen

**When** The screen is first loaded

**Then** A random fact is displayed

**Scenario 2**

**Given** I am on the home screen

**When** I press on the "More facts!" button

**Then** A new random fact is displayed

**And** The previously displayed fact is added to history only after the new fact has been successfully displayed

**Scenario 3**

**Given** I am on the home screen

**When** I press on the "Show history" button

**Then** I am navigated to the history screen*

**And** The list of the last 10 loaded facts is displayed (with newer facts replacing older ones)

- The latest fact should be on top of the list. History shows only previously loaded facts; the currently displayed home fact is excluded. If the new fact fails history remains unchanged.

**Scenario 4**

**Given** I am on the history screen

**When** I press on the "Back" button (use back gesture)

**Then** I am navigated to the home screen

**And** The previous loaded fact is displayed

**Resources**

- [Random facts API Documentation](https://uselessfacts.jsph.pl/)