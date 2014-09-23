package ac.in.iiith.siel.reviews.sentiment.analysis;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class writeFile
{
	public void writetoSeedlist(String adj, int value)
	{
		if(value == 1)
		{
			try 
			{
				FileWriter fw = new FileWriter(SentimentAnalyzer.positiveAdjectivesFilePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.append(adj);
				bw.append("\n");
				bw.flush();
				bw.close();					 
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return;
		}
		else
		{
			try 
			{
				FileWriter fw = new FileWriter(SentimentAnalyzer.negativeAdjectivesFilePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.append(adj);
				bw.append("\n");
				bw.flush();
				bw.close();					 
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}