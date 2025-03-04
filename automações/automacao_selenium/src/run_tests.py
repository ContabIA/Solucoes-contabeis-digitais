import unittest as utest
from sys import argv
from tests.test_enum import test_enum, test_dict

def run_tests(test_suite: utest.TestSuite):
    
    runner = utest.TextTestRunner()
    runner.run(test_suite)


if __name__ == "__main__":
    
    argv = argv[1:]
    
    test_suite = utest.TestSuite()
    test_loader = utest.TestLoader()
    
    if len(argv) == 0:
        test_suite.addTests((test_loader.loadTestsFromTestCase(c) for c in test_dict.values()))
        
    else:
        test_suite = utest.TestSuite()
        
        for test in argv:
            if test in test_enum:
                test_suite.addTest(test_loader.loadTestsFromTestCase(test_dict[test]))
            else:
                print(f"Test {test} not found.")
                
    runner = utest.TextTestRunner()
    runner.run(test_suite)
                