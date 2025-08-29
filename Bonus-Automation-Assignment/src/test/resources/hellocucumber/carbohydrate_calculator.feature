@myteam
Feature: Carbohydrate Calculator Validation

# Test Case: TC_001 - Verify calculation for maintenance goal in metric units
# Test Case: TC_010 - Verify Age input field with non-numeric age
# Test Case: TC_015 - Verify the Clear button clears input fields
# Test Case: TC_016 - Verify calculation with missing weight (missing input)
    
    Background:
        Given the user is on the Carbohydrate Calculator page

    Scenario: Verify calculation for maintenance goal in Metric Units
        Given the user is on the "Metric Units" tab
        And the user enters the following inputs:
            | Age | Gender | Height (cm) | Weight (kg) | Activity Level |
            | 25  | Male   | 180         | 60          | Light          |
        When the user clicks on the "Calculate" button
        Then the daily calorie allowance should be should be "2,207"

    Scenario: Verify Age input with non-numeric value
        When the user enters "twenty" into the Age field
        Then the age input error message "positive numbers only" should be displayed

    Scenario: Verify the Clear button clears input fields
        Given the following inputs are entered:
            | Age | Gender | Height (cm) | Weight (kg) | Activity Level   |
            | 25  | Male   | 180         | 60          | Light exercise   |
        When the user clicks on the Clear button
        Then all input fields should be empty

    Scenario: Verify calculation with missing weight input
        Given the following inputs are entered:
            | Age | Gender | Height (cm) | Weight (kg) | Activity Level   |
            | 25  | Male   | 180         |             | Light   exercise |
        When the user clicks on the Calculate button
        Then an error message "Please enter weight" should be displayed

