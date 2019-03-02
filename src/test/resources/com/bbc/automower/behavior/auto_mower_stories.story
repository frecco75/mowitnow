Narrative:
I want the test to pass

Scenario: Integration test
Given a 5 by 5 lawn
And a mower at position 1 2 N with instructions LFLFLFLFF
And a mower at position 3 3 E with instructions FFRFFRFRRF
When the mowers execute their instructions
Then the 1st mower should be at position 1 3 N
And the 2nd mower should be at position 5 1 E