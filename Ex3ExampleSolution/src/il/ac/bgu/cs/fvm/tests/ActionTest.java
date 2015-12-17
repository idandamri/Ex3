package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import il.ac.bgu.cs.fvm.labels.Action;

/**
 *
 * @author michael
 */
public class ActionTest {

	public ActionTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testReuse() {
		Action actA1 = new Action("alpha");
		Action actA2 = new Action("alpha");

		assertEquals(actA1, actA2);
	}

	@Test
	public void testNonCollision() {
		Action actA = new Action("alpha");
		Action actB = new Action("beta");

		assertNotEquals(actA, actB);
	}

}
