package net.sf.staccato.commons.check;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Test for {@link Check}
 * 
 * @author flbulgarelli
 * 
 */
public class CheckUnitTest {

	private static final String VAR_NAME = "var";

	private Check<IllegalArgumentException> c = Ensure.that();

	@Test(expected = IllegalArgumentException.class)
	public void testCheckIsTrueStringBooleanStringObjectArray() {
		c.isTrue(VAR_NAME, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckIsTrueStringBoolean() {
		c.isTrue(VAR_NAME, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckMatchesStringStringString() {
		c.matches(VAR_NAME, "hello", ".ola.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckMatchesStringStringPattern() {
		c.matches(VAR_NAME, "hello", Pattern.compile(".ola."));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeLong() {
		c.isNotNegative(VAR_NAME, -5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeInt() {
		c.isNotNegative(VAR_NAME, -5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeDouble() {
		c.isNotNegative(VAR_NAME, -5.9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeFloat() {
		c.isNotNegative(VAR_NAME, -5f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeBigDecimal() {
		c.isNotNegative(VAR_NAME, BigDecimal.valueOf(-9.69));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotNegativeBigInteger() {
		c.isNotNegative(VAR_NAME, BigInteger.valueOf(-9));
	}

	@Test
	public void testisNotNegativeLong_Zero() {
		c.isNotNegative(VAR_NAME, 0L);
	}

	@Test
	public void testisNotNegativeInt_Zero() {
		c.isNotNegative(VAR_NAME, 0);
	}

	@Test
	public void testisNotNegativeDouble_Zero() {
		c.isNotNegative(VAR_NAME, 0.0);
	}

	@Test
	public void testisNotNegativeFloat_Zero() {
		c.isNotNegative(VAR_NAME, 0.0f);
	}

	@Test
	public void testisNotNegativeBigDecimal_Zero() {
		c.isNotNegative(VAR_NAME, BigDecimal.ZERO);
	}

	@Test
	public void testisNotNegativeBigInteger_Zero() {
		c.isNotNegative(VAR_NAME, BigInteger.ZERO);
	}

	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testisNotEmptyStringEmptyAware() {
	// fail("Not yet implemented");
	// }

	// @Test(expected = IllegalArgumentException.class)
	// public void testisNotEmptyStringCollectionOfQ() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testisNotEmptyStringIterableOfQ() {
	// fail("Not yet implemented");
	// }
	//
	@Test
	public void testNotEmptyMap() {
		c.isNotEmpty(VAR_NAME, Collections.singletonMap("Hello", "World"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotEmptMap_Fail() {
		c.isNotEmpty(VAR_NAME, Collections.emptyMap());
	}

	@Test
	public void testisNotEmptyCharSequence() {
		c.isNotEmpty(VAR_NAME, "hola");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotEmptyCharSequence_Fail() {
		c.isNotEmpty(VAR_NAME, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testisNotEmptyArray() {
		c.isNotEmpty(VAR_NAME, new byte[0]);
	}

	@Test
	public void testNotEmptyCollection() {
		c.isNotEmpty(VAR_NAME, Collections.singleton(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotEmptyCollection_Fail() {
		c.isNotEmpty(VAR_NAME, Collections.emptyList());
	}

	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckEmptyStringIterableOfQ() {
	// fail("Not yet implemented");
	// }
	//
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckEmptyStringEmptyAware() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringLong() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringInt() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringDouble() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringFloat() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringBigDecimal() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckPositiveStringBigInteger() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckIsInstanceOf() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckSizeStringCollectionOfQInt() {
	// fail("Not yet implemented");
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckSizeStringCharSequenceInt() {
	// fail("Not yet implemented");
	// }
	//
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testCheckSizeStringSizeAwareInt() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testPositive() {
		c
			.isPositive(VAR_NAME, 9)
			.isPositive(VAR_NAME, 9L)
			.isPositive(VAR_NAME, 9f)
			.isPositive(VAR_NAME, 9.0)
			.isPositive(VAR_NAME, BigDecimal.valueOf(69.62))
			.isPositive(VAR_NAME, BigInteger.valueOf(1200));
	}

	@Test
	public void testSize() {
		c
			.isSize(VAR_NAME, Arrays.asList(9, 96), 2)
			.isSize(VAR_NAME, "Hello", 5)
			.isSize(VAR_NAME, new double[] { 5.5, 9 }, 2)
			.isSize(VAR_NAME, new Object[] { 9, 93, 23, 6, 0 }, 5);

	}

	@Test
	public void testEmpty() {
		c
			.isEmpty(VAR_NAME, Collections.emptyList())
			.isEmpty(VAR_NAME, Collections.emptyMap())
			.isEmpty(VAR_NAME, Collections.<String> emptyList());
	}

	@Test
	public void testIsInstanceOf() {
		c.isInstanceOf(VAR_NAME, 5, Number.class);
	}

	@Test
	public void testIsTrue() {
		c.isTrue(VAR_NAME, true);
	}

	@Test
	public void testMatchesStringStringPattern() {
		c.matches(VAR_NAME, "Hello", ".*ell.");
	}

	@Test
	public void testMatchesStringStringString() {
		c.matches(VAR_NAME, "Hello", Pattern.compile(".*ell."));
	}

	@Test
	public void testNonNegative() {
		c
			.isNotNegative(VAR_NAME, 9)
			.isNotNegative(VAR_NAME, 9L)
			.isNotNegative(VAR_NAME, 9f)
			.isNotNegative(VAR_NAME, 9.0)
			.isNotNegative(VAR_NAME, BigDecimal.valueOf(69.62))
			.isNotNegative(VAR_NAME, BigInteger.valueOf(1200));
	}

	@Test
	public void testNotNull() {
		c.isNotNull(VAR_NAME, new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotNull_Fail() {
		c.isNotNull(VAR_NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFail() {
		c.fail(VAR_NAME, "Foo", "Should be palindromic");
	}

}
