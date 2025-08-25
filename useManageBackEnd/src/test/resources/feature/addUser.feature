Feature: Add new user

  @AddUser
  Scenario Outline: Administrator adds new user successfully
    Given Administrator is logged In
    Then Administrator clicks the add user button
    When Administrator adds valid user "<name>", "<username>" and "<email>"
    And Administrator submits user details
    Then Administrator is taken to the dashboard

    Examples:
      | name | username | email            |
      | Will | WillMan  | willman@mail.com |
