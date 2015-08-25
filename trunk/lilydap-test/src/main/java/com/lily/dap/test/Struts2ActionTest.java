package com.lily.dap.test;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsSpringTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class Struts2ActionTest extends StrutsSpringTestCase {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** The transaction manager to use */
	protected PlatformTransactionManager transactionManager;

	/** Should we roll back by default? */
	private boolean defaultRollback = true;

	/** Should we commit the current transaction? */
	private boolean complete = false;

	/**
	 * Transaction definition used by this test class: by default, a plain
	 * DefaultTransactionDefinition. Subclasses can change this to cause
	 * different behavior.
	 */
	protected TransactionDefinition transactionDefinition= new DefaultTransactionDefinition();

	/**
	 * TransactionStatus for this test. Typical subclasses won't need to use it.
	 */
	protected TransactionStatus transactionStatus;

	@Override
	protected String[] getContextLocations() {
		return new String[]{"classpath*:applicationContext-web.xml"};
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
//		SessionFactory sessionFactory = lookupSessionFactory(request);  
//		Session hibernateSession= getSession(sessionFactory);  
//		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(hibernateSession));	

		this.transactionManager = lookupPlatformTransactionManager(request);
		this.complete = !this.isRollback();

		if (this.transactionManager == null) {
			this.logger.info("No transaction manager set: test will NOT run within a transaction");
		}
		else if (this.transactionDefinition == null) {
			this.logger.info("No transaction definition set: test will NOT run within a transaction");
		}
		else {
			startNewTransaction();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		if (this.transactionStatus != null && !this.transactionStatus.isCompleted()) {
			endTransaction();
		}
	}

	/**
	 * Subclasses can set this value in their constructor to change the default,
	 * which is always to roll the transaction back.
	 */
	public void setDefaultRollback(final boolean defaultRollback) {
		this.defaultRollback = defaultRollback;
	}
	/**
	 * Get the <em>default rollback</em> flag for this test.
	 * @see #setDefaultRollback(boolean)
	 * @return The <em>default rollback</em> flag.
	 */
	protected boolean isDefaultRollback() {
		return this.defaultRollback;
	}

	/**
	 * Determines whether or not to rollback transactions for the current test.
	 * <p>The default implementation delegates to {@link #isDefaultRollback()}.
	 * Subclasses can override as necessary.
	 */
	protected boolean isRollback() {
		return isDefaultRollback();
	}

	/**
	 * Call this method in an overridden {@link #runBare()} method to prevent
	 * transactional execution.
	 */
	protected void preventTransaction() {
		this.transactionDefinition = null;
	}

	/**
	 * Call this method in an overridden {@link #runBare()} method to override
	 * the transaction attributes that will be used, so that {@link #setUp()}
	 * and {@link #tearDown()} behavior is modified.
	 * @param customDefinition the custom transaction definition
	 */
	protected void setTransactionDefinition(TransactionDefinition customDefinition) {
		this.transactionDefinition = customDefinition;
	}

//	private Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {     
//		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
//		
//		FlushMode flushMode = FlushMode.MANUAL;     
//		if (flushMode != null) {        
//			session.setFlushMode(flushMode);     
//		}      
//		
//		return session; 
//	} 
//	
//	@SuppressWarnings("static-access")
//	private SessionFactory lookupSessionFactory(HttpServletRequest request) {     
//		return (SessionFactory)this.applicationContext.getBean("sessionFactory");
//	}
	
	@SuppressWarnings("static-access")
	private PlatformTransactionManager lookupPlatformTransactionManager(HttpServletRequest request) {     
		return (PlatformTransactionManager)this.applicationContext.getBean("transactionManager");
	}

	/**
	 * Start a new transaction. Only call this method if
	 * {@link #endTransaction()} has been called. {@link #setComplete()} can be
	 * used again in the new transaction. The fate of the new transaction, by
	 * default, will be the usual rollback.
	 * @throws TransactionException if starting the transaction failed
	 */
	protected void startNewTransaction() throws TransactionException {
		if (this.transactionStatus != null) {
			throw new IllegalStateException("Cannot start new transaction without ending existing transaction: "
					+ "Invoke endTransaction() before startNewTransaction()");
		}
		if (this.transactionManager == null) {
			throw new IllegalStateException("No transaction manager set");
		}

		this.transactionStatus = this.transactionManager.getTransaction(this.transactionDefinition);
		this.complete = !this.isRollback();

		if (this.logger.isDebugEnabled())
			this.logger.debug("Began transaction: transaction manager [{}]; rollback [{}].", this.transactionManager, this.isRollback());
	}

	protected void endTransaction() {
		final boolean commit = this.complete || !isRollback();
		if (this.transactionStatus != null) {
			try {
				if (commit) {
					this.transactionManager.commit(this.transactionStatus);
					this.logger.debug("Committed transaction after execution of test [{}].", getName());
				}
				else {
					this.transactionManager.rollback(this.transactionStatus);
					this.logger.debug("Rolled back transaction after execution of test [{}].", getName());
				}
			}
			finally {
				this.transactionStatus = null;
			}
		}
	}
}
