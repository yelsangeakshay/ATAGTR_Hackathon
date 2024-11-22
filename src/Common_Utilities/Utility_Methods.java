package Common_Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.yaml.snakeyaml.Yaml;

public class Utility_Methods {

	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final SecureRandom random = new SecureRandom();

	public static String generateRandomAlphanumericString() {
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 5; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}

		return sb.toString();

	}

	public static boolean deleteDirectory(File directory) {
		boolean directorydeleted = directory.delete();
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectory(file);
					} else {
						file.delete();
					}
				}
			}
			directorydeleted = directory.delete();
		} else {
			System.out.println("Directory does not exist.");
		}
		return directorydeleted;
	}

	public static File CreateLogDirectory(String dirName) {

		String projectDir = System.getProperty("user.dir");

		File directory = new File(projectDir + "\\" + "TestLogs" + "\\" + dirName);

		if (directory.exists()) {
			// System.out.println(directory + " , already exists");
			deleteDirectory(directory);
			directory.mkdir();
			System.out.println(directory.getName() + " , created");
		} else {
			// System.out.println(directory + " , doesnt exists , hence creating it");
			directory.mkdir();
			//System.out.println(directory.getName() + " , created");
		}

		return directory;
	}

	public static String pagename(String input) {
		String title = input.replaceAll("[:|. ']", "_");
		return title;
	}

	public static void evidenceFileCreator(String Filename, File FileLocation, String endpoint, String RequestBody,
			String ResHeader, String ResponseBody) throws IOException {
		File newTextFile = new File(FileLocation + "\\" + Filename + ".txt");
		// System.out.println("File create with name: " + newTextFile.getName());
		FileWriter writedata = new FileWriter(newTextFile);
		writedata.write("Endpoint is :\n" + endpoint + "\n\n");
		writedata.write("Request body is :\n" + RequestBody + "\n\n");
		writedata.write("Response header date is : \n" + ResHeader + "\n\n");
		writedata.write("Response body is : \n" + ResponseBody);
		writedata.close();

	}

	public static String testLogName(String Name) {
		LocalTime currentTime = LocalTime.now();
		String currentstringTime = currentTime.toString().replaceAll(":", "");
		String TestLogName = Name + currentstringTime;
		return TestLogName;
	}

	public static void readDatafromExcel(String filename, String Sheetname, String TestCaseName) throws IOException {

		String projectFolder = System.getProperty("user.dir");

		FileInputStream fis = new FileInputStream(projectFolder + "\\DataFiles\\" + filename + ".xlsx");

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		int countofsheets = wb.getNumberOfSheets();

		for (int i = 0; i < countofsheets; i++) {
			String sheetname = wb.getSheetName(i);
			if (sheetname.equals(Sheetname)) {
				XSSFSheet sheet = wb.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				while (rows.hasNext()) {

					Row datarows = rows.next();
					String tc_name = datarows.getCell(0).getStringCellValue();
					if (tc_name.equals(TestCaseName)) {
						Iterator<Cell> celldata = datarows.cellIterator();
						while (celldata.hasNext()) {
							Cell cd = celldata.next();
							String testdata = cd.getStringCellValue();
							System.out.println(testdata);
						}
						break;
					} else {
						System.out.println("Desired " + TestCaseName + " was not found in " + sheetname + " of file "
								+ filename + ".xlsx");
					}

				}
				break;
			} else {
				System.out.println("Desired " + Sheetname + " was not found in " + filename + ".xlsx");
			}
		}

	}

	public static Map<String, Object> readYamlFile(String fileName) {
		Yaml yaml = new Yaml();
		Map<String, Object> map = null;
		/*
		 String projectRoot = System.getProperty("user.dir");
	        String Name = "Variable.yaml";

	        System.out.println("Searching for file: " + Name + " in directory: " + projectRoot);

	        File result = findFile(new File(projectRoot), Name);

	        if (result != null) {
	            System.out.println("File found at: " + result.getAbsolutePath());
	        } else {
	            System.out.println("File not found.");
	        }
	        */
	    
		 try (FileInputStream inputStream = new FileInputStream(fileName)) {
	            return yaml.load(inputStream);
	        } catch (Exception e) {
	            throw new RuntimeException("Error reading YAML file: " + fileName, e);
	        }
		/*

		try (InputStream inputStream = Utility_Methods.class.getClassLoader().getResourceAsStream(fileName)) {
			if (inputStream != null) {
				map = yaml.load(inputStream);
			} else {
				System.err.println("File not found: " + fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
		*/

	}
	
	 public static File findFile(File directory, String fileName) {
	        if (directory == null || !directory.exists()) {
	            return null;
	        }

	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    // Recursive call for subdirectories
	                    File found = findFile(file, fileName);
	                    if (found != null) {
	                        return found;
	                    }
	                } else if (fileName.equals(file.getName())) {
	                    return file; // File found
	                }
	            }
	        }
	        return null; // File not found
	    }
}
