package qualityChecker;

import java.util.*;

public class SpellCorrector
{
	TreeSet<String> dictionary;
	
	public SpellCorrector()
	{
		
	}
	
	public void useDictionaryContent(String content)
	{
		dictionary = new TreeSet<String>();
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter(",");
		while (scanner.hasNext())
		{
			String stuff = scanner.next().toLowerCase();
			dictionary.add(stuff);
		}
		scanner.close();
	}
	
	private ArrayList<String> deletion(String inputWord)
	{
		ArrayList<String> possibles = new ArrayList<String>();
		for (int k = 0; k < inputWord.length(); k++)
		{
			char[] word = inputWord.toCharArray();
			ArrayList<Character> word2 = new ArrayList<Character>();
			for (int i = 0; i < word.length; i++)
			{
				word2.add(word[i]);
			}
			word2.remove(k);
			String newWord = "";
			for (int i = 0; i < word2.size(); i++)
			{
				String letter = word2.get(i) + "";
				newWord = newWord + letter;
			}
			possibles.add(newWord);
		}
		return possibles;
	}
	
	private ArrayList<String> transposition(String inputWord)
	{
		ArrayList<String> possibles = new ArrayList<String>();
		for (int i = 1; i < inputWord.length(); i++)
		{
			char[] word = inputWord.toCharArray();
			char temp1 = word[i-1];
			char temp2 = word[i];
			word[i-1] = temp2;
			word[i] = temp1;

			String newWord = "";
			for (int k = 0; k < word.length; k++)
			{
				String letter = word[k] + "";
				newWord = newWord + letter;
			}
			possibles.add(newWord);
		}
		return possibles;
	}
	
	private ArrayList<String> alteration(String inputWord)
	{
		ArrayList<String> possibles = new ArrayList<String>();
		for (int k = 0; k < inputWord.length(); k++)
		{
			char[] word = inputWord.toCharArray();
			ArrayList<Character> word2 = new ArrayList<Character>();
			for (int i = 0; i < word.length; i++)
			{
				word2.add(word[i]);
			}
			word2.remove(k);
			for (int i = 97; i < 123; i++)
			{
				word2.add(k, (char)i);
				String newWord = "";
				for (int x = 0; x < word2.size(); x++)
				{
					String letter = word2.get(x) + "";
					newWord = newWord + letter;
				}
				possibles.add(newWord);
				word2.remove(k);
			}
		}
		return possibles;
	}
	
	private ArrayList<String> insertion(String inputWord)
	{
		ArrayList<String> possibles = new ArrayList<String>();
		for (int k = 0; k <= inputWord.length(); k++)
		{
			char[] word = inputWord.toCharArray();
			ArrayList<Character> word2 = new ArrayList<Character>();
			for (int i = 0; i < word.length; i++)
			{
				word2.add(word[i]);
			}
			for (int i = 97; i < 123; i++)
			{
				word2.add(k, (char)i);
				String newWord = "";
				for (int x = 0; x < word2.size(); x++)
				{
					String letter = word2.get(x) + "";
					newWord = newWord + letter;
				}
				possibles.add(newWord);
				word2.remove(k);
			}
		}
		return possibles;
	}
	
	public TreeSet<String> findSimilarWords(String inputWord)
	{
		ArrayList<String> similar; 
		ArrayList<String> possibles = new ArrayList<String>();
		ArrayList<String> allPossibles = new ArrayList<String>();
		
		similar = deletion(inputWord);
		possibles.addAll(similar);
		
		similar = transposition(inputWord);
		possibles.addAll(similar);
		
		similar = alteration(inputWord);
		possibles.addAll(similar);
		
		similar = insertion(inputWord);
		possibles.addAll(similar);
		
		allPossibles = weedFakeWords(possibles);
		if (allPossibles.size() == 0)
		{
			for (int i = 0; i < possibles.size(); i++)
			{
				similar = deletion(possibles.get(i));
				allPossibles.addAll(similar);
				
				similar = transposition(possibles.get(i));
				allPossibles.addAll(similar);
				
				similar = alteration(possibles.get(i));
				allPossibles.addAll(similar);
				
				similar = insertion(possibles.get(i));
				allPossibles.addAll(similar);
			}
			allPossibles = weedFakeWords(allPossibles);
		}
		TreeSet<String> result = new TreeSet<String>();
		result.addAll(allPossibles);
		return result;
	}
	
	public boolean contains(String word)
	{
		return dictionary.contains(word.toLowerCase());
	}
	
	public String toString()
	{
		return dictionary.toString();
	}
	
	public boolean isEmpty()
	{
		if (dictionary.size() == 0)
		{
			return true;
		}
		return false;
	}
	
	private ArrayList<String> weedFakeWords(ArrayList<String> similar)
	{
		ArrayList<String> possibles = new ArrayList<String>();
		for (int i = 0; i < similar.size(); i++)
		{
			if (dictionary.contains(similar.get(i)))
			{
				possibles.add(similar.get(i));
			}
		}
		return possibles;
	}
}
