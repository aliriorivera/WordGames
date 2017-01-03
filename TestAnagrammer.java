package hw3;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAnagrammer {	
	String key = "loop";
	WordGame nam = new Anagrammer();
	List<String> anagrams = ((Anagrammer)nam).anagramMap.get(key);;


	@Test
	public void testAnagramCount() {
		assertEquals ("Test1. Test count anagrams", 3, anagrams.size());		
	}

	@Test
	public void testAnagramWords(){
		assertEquals ("Test2. Test anagram words", true, anagrams.contains("loop"));	
		assertEquals ("Test2. Test anagram words", true, anagrams.contains("polo"));	
		assertEquals ("Test2. Test anagram words", true, anagrams.contains("pool"));
	}
}
