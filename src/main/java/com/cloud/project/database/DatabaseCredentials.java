package com.cloud.project.database;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DatabaseCredentials {

	private String configFilePath = "db_config.xml";
	private String instanceConnectionName;
	private String username;
	private String password;

	private static final String INSTANCE_NODE = "instance_name";
	private static final String USERNAME_NODE = "username";
	private static final String PASSWORD_NODE = "password";

	private final String sqlUrl;

	public DatabaseCredentials() throws ParserConfigurationException, SAXException, IOException {
		loadCredentials();
		sqlUrl = "jdbc:mysql://google/CLOUDCOMPUTING" + "?cloudSqlInstance=" + instanceConnectionName
				+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=" + username + "&password=" + password
				+ "&useSSL=false";
	}

	private void loadCredentials() throws ParserConfigurationException, SAXException, IOException {
		File xmlConfigFile = new File(DatabaseCredentials.class.getClassLoader().getResource(configFilePath).getFile());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlConfigFile);

		NodeList nList = doc.getElementsByTagName("database");
		NodeList credentials = nList.item(0).getChildNodes();

		for (int i = 0; i < credentials.getLength(); i++) {
			Node node = credentials.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				switch (element.getNodeName()) {
				case INSTANCE_NODE:
					instanceConnectionName = element.getTextContent();
					break;
				case USERNAME_NODE:
					username = element.getTextContent();
					break;
				case PASSWORD_NODE:
					password = element.getTextContent();
					break;
				}
			}
		}
	}

	public String getSqlUrl() {
		return sqlUrl;
	}

}
