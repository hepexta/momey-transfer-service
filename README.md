# money-transfer-service

The implementation a classic money transfer service in Java that is thread-safe and lock-free. It ensures that concurrent transactions between multiple threads do not interfere with each other. It uses Atomic variables (from java.util.concurrent.atomic package) to provide atomicity and avoid locks while ensuring thread safety.

Key Test Cases:

- testInitialBalance: Checks the initial balance of the accounts.
- testDeposit: Tests the deposit operation.
- testWithdrawSufficientBalance: Tests withdrawal when there are sufficient funds.
- testWithdrawInsufficientBalance: Tests withdrawal when there are insufficient funds.
- testTransferSufficientBalance: Tests transferring money between accounts when there are sufficient funds.
- testTransferInsufficientBalance: Tests transferring money when there are insufficient funds.
- testNegativeDepositThrowsException: Ensures negative deposit throws an IllegalArgumentException.
- testNegativeWithdrawThrowsException: Ensures negative withdrawal throws an IllegalArgumentException.

By using multiple threads and controlled synchronization with CountDownLatch, this approach allows you to test the thread safety of the MoneyTransferService. Ensuring that concurrent operations yield consistent results without race conditions validates the thread safety of the implementation.