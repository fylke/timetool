package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.DummyHandler;

public class ProjectHandlerTest {
	private static CompanyFactory pf;
	private static Reader compInput;
	private static XMLReader reader;
	private static DummyHandler testHandler;
	private static Project testProj;
	private static ProjectHandler projHandler;

	private static final int testProjId = 10;
	private static final String ns = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pf = new CompanyFactory();

		try {
			reader =
				XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		}
		catch (final SAXException e) {
			try {
				reader = XMLReaderFactory.createXMLReader();
			} catch (final SAXException e1) {
				throw new NoClassDefFoundError("No SAX parser is available.");
			}
		}

		testProj = new Project();
		testHandler = new DummyHandler(reader);

		reader.setContentHandler(testHandler);

		final AttributesImpl dummyAttr = new AttributesImpl();
		projHandler = new ProjectHandler(dummyAttr, reader, testHandler,
																				testProj, ns);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		compInput.close();
	}

	@Test
	public final void testProjectHandlerSimple() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerComp = 1;
		final int projDepth = 0;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);
		final StringBuilder sb = new StringBuilder();

		final String testProjInput = pf.getXmlProject(testProjId, projDepth, compConfig, sb);
		final Project projKey = pf.getProject(testProjId, projDepth, compConfig);

		compInput = new StringReader(testProjInput);

		testHandler.setHandlerToTest(projHandler);

		reader.parse(new InputSource(compInput));

		assertEquals(projKey, testProj);
	}

	@Test
	public final void testProjectHandlerNested() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerComp = 1;
		final int projDepth = 1;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);
		final StringBuilder sb = new StringBuilder();

		final String testProjInput = pf.getXmlProject(testProjId, projDepth, compConfig, sb);
		final Project projKey = pf.getProject(testProjId, projDepth, compConfig);

		compInput = new StringReader(testProjInput);

		testHandler.setHandlerToTest(projHandler);

		reader.parse(new InputSource(compInput));

		assertEquals(projKey, testProj);
	}

	@Test
	public final void testProjectHandlerDeeplyNested() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerComp = 1;
		final int projDepth = 5;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);
		final StringBuilder sb = new StringBuilder();

		final String testProjInput = pf.getXmlProject(testProjId, projDepth, compConfig, sb);
		final Project projKey = pf.getProject(testProjId, projDepth, compConfig);

		compInput = new StringReader(testProjInput);

		testHandler.setHandlerToTest(projHandler);

		reader.parse(new InputSource(compInput));

		assertEquals(projKey, testProj);
	}
}