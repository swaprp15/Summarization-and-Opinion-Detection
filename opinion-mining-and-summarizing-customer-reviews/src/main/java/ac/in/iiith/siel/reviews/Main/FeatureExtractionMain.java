//package ac.in.iiith.siel.reviews.Main;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import ac.in.iiith.siel.reviews.domain.Dependency;
//import ac.in.iiith.siel.reviews.domain.Sentence;
//import ac.in.iiith.siel.reviews.domain.Word;
//import ac.in.iiith.siel.reviews.sentence.dependency.parsing.DependencyParser;
//import ac.in.iiith.siel.reviews.sentence.dependency.parsing.StanfordDependencyParser;
//import ac.in.iiith.siel.reviews.sentence.pos.tagging.OpenNLPPosTagger;
//import ac.in.iiith.siel.reviews.sentence.tokenizer.OpenNLPSentenceTokenizer;
//
//public class FeatureExtractionMain {
//
//	public static String REVIEW_1 = "After reading so many reviews of Windows Phone on Flipkart, where people complain regarding missing features of this OS, in lack of knowledge, and no place to answer them, I thought to Right this review to kill the wrong myth spreading regarding windows phones and Nokia Lumia:-" +
//									"#You can share Photos, videos, Music via Bluetooth. (Caution- The option is only visible only if the phone is connected to a device via BT. Connect first in settings and then try to share)." +
//									"#Multiple file sharing is only available for pics for now. You can select as many photos u want and send them in one go via BT. Music/videos are sent one by one." +
//									"#To save Battery, read the tips in Nokia Care app which comes preinstalled in phone. Also there are many helpful tips there." +
//									"#Gmail is working perfectly fine on windows phone. Even if Google turn off Active sync in july, Microsoft will update the client with new meathod to sync. But, its been adviced that you make your Microsoft Live account (outlook) as main account and try to shift your work to it. Moreover, Outlook service is far more better than Gmail. Try it." +
//									"#Apps are coming. We now have Temple Run 1 in WP store, so its sure we will get updates from that developer. Moreover, as WP is gaining popularity, developers are surely taking this platform seriously & coming with more great apps for it. And saying buy an android for now till apps come on wp will not solve problem. Buy more windows phones, sooner you will get more new apps." +
//									"#No words on instagram for WP yet. But looking at Nokia's #2instawithlove campaign. its not far when we will see instagram for windowsphone. BTW, an app called instagraph is coming which allow to share pics on instagram." +
//									"#Please don't ask for any more Music Player. We have many (few names include Tune wiki, Lyrics, Mixtapes, Album Flow, TuneIn, AUPEO, Soundtracker, etc) but Nokia Music is ultimate, try it, and you will never ask for any other player again." +
//									"#You can of course Fast forward/Reverse Songs/videos. Not by seek bar, but by long pressing Next/previous buttons. Remember, if you can't find any option, try Longpressing, it always turns out useful." +
//									"#To download youtube videos, Try Metrotube." +
//									"# One more thing I would like to clear before you buy this phone & post bad reviews, that if you thinking to use this phone as your Pendrive to carry unsupported data (yes, many of users complain that they can't transfer unsupported files to WP to carry them on phone itself), then mind it, it can't do that, Microsoft is very serious regarding quality and security in Windows Phones. Moreover, if you can't live without downloading pirated apps/movies/music from illeagal sites (though we have Nokia Music & Free Movie Streaming apps) on your phone itself, then Keep Away! Its a WINDOWS PHONE, piracy is strictly not allowed. And regarding FREE APPS, of cours there are many in store. Moreover, you can download almost all paid apps/Games on Trial. To do so, just go to any paid app, see if its available on trial, TRY option will be there on bottom left of screen. That trial app/game would be downloaded for free, & would be either Fully functional but ad supported, or would be usable for limited time (but its rare) or will have Some advanced Features/Levels Restricted. In the latter case, you can use free app till its limit, & if u like, then buy it. SORRY, NO MEATHOD TO DOWNLOAD THE PAID VERSION FOR FREE!! U know, NO PIRACY...";
//
//	public static String REVIEW_2 = "This phone is awesome, and will not disappoint you in any case except mentioned above. Highly Recommended, worth every penny you invest. enjoy" + 
//									"Learn How to Write a Review!!" +
//									"Fellow Flipkarters..!!" +
//									"I'm not here to write all the pros and cons of this phone. That's already been done by many before. What I wanna say is that, I observed most of the reviews are not actually reviews. Don't know why people write everything that comes to their mind. Art of reviewing or being a critic needs some skills to look into things profoundly. I here by wanna list some lines that fellow reviewers wrote in their reviews which were actually funny in a way." +
//									"#There is no secondary camera in this phone" +
//									"- C'mon guys.. It's like you went to a bollywood movie and saying that Brad Pitt is missing in this movie. This is bottom version of windows8." +
//									"# Pictures taken at low light are not crisp" +
//									"- It's clearly stated that there is no LED flash for camera. Still I donno which moron tries to take pics in dull light with this budget phone." +
//									"# Temple Run doesn't work smooth" +
//									"You are on city roads and you want to hit your top speed?? Get onto an highway bro.. Keep in mind that u paid only 10 grand for this phone." +
//									"Criticism is not a Kids play. Better concentrate on defects in what you got rather than complaining what is missing. Or if you think that there is a better option than this phone within the price range, then name it and compare it.";
//
//	public static String REVIEW_3 = "There is the BIG question. 620 or 520? Let's see what you get by investing 3.5k more:-" +
//									"#Nokia Clear Black Screen ie, Extra Bright Screen to be readable Under sun, and with deep black, it gives better experience." +
//									"#Front Facing VGA camera for Video calling needs" +
//									"#LED flash" +
//									"#Magnetometer (compass) sensor to use apps like Here Maps, Here City Lens, ets which uses compass to fing your location, and its really necessary if you gonna use Maps a lot." +
//									"#Extra Loud Speaker" +
//									"#Secondary HAAC mic for noise cancellation while Calling, voice&video recording, Music playback." +
//									"#Dolby Headphones Enhancements to give theater like feel while listening music on headphones." +
//									"#Equalizer with 19 presets like Rock, pop, classical, bass etc." +
//									"#Smaller screen with same resolution, that means denser ppi, and more clear and crisp screen. (If you want bigger screen, 520 is not that bad either in terms of ppi, but Lack of Clear Black is surely missed)" +
//									"#Toughned Glass with protection from scratches. (520 has just scratch proof layer, not that tough glass)" +
//									"#NFC, to use tap and share, tap and play, NFC tags, etc." +
//									"#Though Battery is smaller in 620, but due to smaller screen, Clear black display and NO super sensitive touch, both will give almost similar backup." +
//									"#Longer Nokia Music Unlimited Subscription." +
//									"#An option to increase 620's durability by installing a Water-proof, dust proof back cover. Its not available for 520." +
//									"That's surely worth the 3.5k bucks. If you are looking for a phone, which you gonna use for long term, then you should better buy 620, or if your pocket permits, then 720. I feel 520 is for those who changes phone every now and then, and for those who have Budget of 12k dot." +
//									"So, I have said more than enough. Hopefully, you Liked my openion. Hint me by giving it a like." +
//									"Having question regarding windows phone os/ this phone? you can ask directly to Nokia on twitter @NokiaHelps, they really give instant help. or ask me." +
//									"(This was in addition to my original review~Ayush Mathur)" + 
//									"Value for money redefined :)";
//	
//	public static String REVIEW_4 = "Go ahead, buy this phone, you wont regret :) Nokia Phone with WINDOWS PHONE 8 (NOT SEVEN!!!), at ONLY 10499! Do I need to say more?" +
//									"I used this device at Nokia Care in my city. This is an amazing phone. It will change the definition of Budget Phones. I mean, its hard to believe that a Windows Phone EIGHT! device is availble for just 10k!" +
//									"It has same processing hardware as in Nokia Lumia 720 which is expected to be priced 10k more than this. Dual core processor, dedicated GPU, 4 inch IPS LCD Screen, Sleek Looks, Super sensitive touch, Expandable memory, 5MP Auto Focus Camera, 720p Video Recording are just a few Highlights of this phone. Though 512 MB RAM is low, but you can't ask more at this price point. This is just like Nokia has listened to us and made a Phone just for INDIANS" +
//									"To add, they have given all Nokia exclusive features like HERE Map, drive, transit; Cinemagraph, Smartshoot, Panorama lenses; Nokia Music with free music download for 3 months, & not to forget those 3G Data packs by operators." +
//									"Talking about design, one can easily say that its the best looking phone out there not only in its price range but also in the range till Lumia 720. The sleek look, bright funky colors, rectangular design, can easily make anyone say awww so cute, I want it...!!!" +
//									"Nokia has also given an option to exchange shells. So you can buy an extra shell of any color. It is specially useful for those who want a colourful phone but office standards restricts them. Buy a black/white one extra and your problem is solved." +
//									"Talking about Performance, as said earlier, it has same processor and chipset as of its elder bros, so there is not a single point of compromise here. It will respond faster, will not lag (though heavy task is of-course restricted on it and on 620,720 too & by heavy task I mean playing memory hungry games/apps), has enough internal memory (8GB!) to accommodate all your apps, games and offline maps. so there is definitely a huge improvement to the Lumia 510, which was badly criticised due to its low memory. Nokia has learned! But still, that phone was bestseller, I wonder what this phone will do?" +
//									"It has Office 365, email and linkedin integration. So if you are a hard working guy, it will help you to do your job smartly, without even hurting your pocket." +
//									"If you want a phone for your social needs, then don't look for any other phone. Windows Phones are best in this category as they have Facebook, Twitter,skype integration, and a lot of IM apps to keep you connected. People Hub, merges your contacts from FB, Twitter, Linkedin and show all of their latest updates on single tile (if you wish to). Ah, I know Facebook has launched so called Facebook Home (actually its an Android Launcher for all android devices), which brings facebook integration to android phone, but believe me, its nothing different from windows phone integration. Moreover, it will create problems in laggy androids, can't say if it will run on budget devices too." +
//									"High End Gamers, Ignore it. Enough said." +
//									"Casual Gamers, it has collection of games till Ashphalt 7 Heat. Temple Run is not yet available, but its said that lower version coming. By the time you can play games like Angry birds (all), xboxlive games (Yahtzee, Skulls of the shogun, AlphaJax, Wordament, Monopoly, NFS undercover, shift, etc) and beside that, there are many more non xboxlive games like from gameloft (Amazing Spiderman, Real soccer, Dark Knight, etc) and from other famous developers." +
//									"Music addicts, go for it. It has nice music player. Nokia Music service provided by nokia completes multimedia needs. No need of any other apps. It allows free music download (free for 3 months, afterward, subscription can be extended by giving nominal charges), Mix radio plays random music of your choice, lyrics are shown with just a tap. Sound is very nice. Though it lacks Dolby enhancements, but still the plain music sounds too good. The high bass levels do not distort if Good quality headphones are used, which is not the case with other competitive phones, & rest assured, you will feel the beat." +
//									"Video playback is good. Almost all standard formats are supported, but yeh, formats like mkv, which are quite famous on torrent are not supported. Youtube videos plays like water flow, conditioned you are using 3g data pack/high speed wifi network. 2G can't handle smooth play, but pre downloading is smooth in 2g speed. Apps are available to download youtube videos." +
//									"Photography on this phone is average. Its 5MP camera is good. Performs well against similar rivals. But one thing you will surely like about the camera is its capability to record 720p HD videos at 30fps. That's the thing which no phone provide till now under 11k price range (of-course I am not considering non branded/cheap brand phones)." +
//									"So from my side, its a big thumbs up but Lumia 620 is much better option. Look for another review of Difference between L520 & L620."; 
//
//
//	public static void main(String[] args) throws Exception {
//		Map<String, Integer> featureIdMapping = new HashMap<String, Integer>();
//
//		List<String> reviews = Arrays.asList(REVIEW_1, REVIEW_2, REVIEW_3,
//				REVIEW_4);
//		BufferedWriter featuresWriter = new BufferedWriter(new FileWriter(
//				"features.list"));
//		BufferedWriter transactionListWriter = new BufferedWriter(
//				new FileWriter("transaction.list"));
//		List<Sentence> allSentences = new ArrayList<Sentence>();
//
//		DependencyParser parser = new StanfordDependencyParser();
//		OpenNLPSentenceTokenizer tokenizer = new OpenNLPSentenceTokenizer(
//				"models/en-sent.bin", "models/en-token.bin");
//		int id = 1;
//
//		for (String review : reviews) {
//			List<Sentence> sentences = tokenizer.toSentences(review);
//
//			for (Sentence sentence : sentences) {
//				parser.parse(sentence);
//				for (Dependency dep : sentence.getFeatureOpinionPairs()) {
//					 System.out.println(dep.getDependencyName()+"(" +
//					 dep.getGoverner().getLabel() + "-" +
//					 dep.getGoverner().getWordNumber() + ","
//					 + dep.getDependent().getLabel() + "-" +
//					 dep.getDependent().getWordNumber() + ")");
//				}
//			}
//
//			
//			SimpleStemmer stemmer = new SimpleStemmer();
//			SimpleStopWordsFilter stopWordsFilter = new SimpleStopWordsFilter();
//			
//			for (Sentence sentence : sentences) {
//				for (String feature : sentence.getCandidateFeatures()) {					
//					if(!stopWordsFilter.isStopWord(feature)) {
//					String stemmedFeature = stemmer.stem(feature);
//					Integer fid = featureIdMapping.get(stemmedFeature.toLowerCase());
//					if (fid != null) {
//						sentence.addFeatureId(fid);
//					} else {
//						featureIdMapping.put(stemmedFeature.toLowerCase(), id);
//						sentence.addFeatureId(id);
//						id++;
//					}
//					}
//				}
//				allSentences.add(sentence);
//			}
//		}
//
//		Map<String, Integer> sortedFeatureMap = sortMap(featureIdMapping);
//		StringBuffer featureIds = new StringBuffer();
//		StringBuffer features = new StringBuffer();
//
//		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
//				sortedFeatureMap.entrySet());
//
//		for (int i = 0; i < entryList.size(); i++) {
//			featureIds.append(entryList.get(i).getValue());
//			features.append(entryList.get(i).getKey());
//			if (i < (entryList.size() - 1)) {
//				featureIds.append(",");
//				features.append(",");
//			}
//		}
//
//		featuresWriter.write(featureIds.toString());
//		featuresWriter.write(System.getProperty("line.separator"));
//		featuresWriter.write(features.toString());
//
//		featuresWriter.flush();
//		featuresWriter.close();
//
//		int maxFeatureId = entryList.get(entryList.size() - 1).getValue();
//		 
//		for (Sentence sentence : allSentences) {
//			List<Integer> sortedIds = sentence.getFeatureIds();
//			String str = "";
//
//			if (!sortedIds.isEmpty()) {
//				for (int i = 0; i < sortedIds.size(); i++) {
//					if (i == 0) {
//						str = binaryString(0, sortedIds.get(i), str);
//					} else {
//						str = binaryString(sortedIds.get(i - 1),
//								sortedIds.get(i), str);
//					}
//
//				}
//
//				str = appendZeros(sortedIds.get(sortedIds.size() - 1),
//						maxFeatureId, str);
//				transactionListWriter.write(str);
//				transactionListWriter.write(System.getProperty("line.separator"));
//			}
//
//		}
//
//		transactionListWriter.flush();
//		transactionListWriter.close();
//		System.out.println("featureIds:" + featureIds);
//		System.out.println("features:" + features);
//	}
//
//	private static Map<String, Integer> sortMap(
//			final Map<String, Integer> unsortedMap) {
//
//		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(
//				unsortedMap.entrySet());
//
//		// sort list based on comparator
//		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
//			public int compare(Entry<String, Integer> o1,
//					Entry<String, Integer> o2) {
//				return o1.getValue().compareTo(o2.getValue());
//			}
//		});
//
//		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
//
//		for (Iterator<Entry<String, Integer>> it = list.iterator(); it
//				.hasNext();) {
//			Map.Entry<String, Integer> entry = it.next();
//			sortedMap.put(entry.getKey(), entry.getValue());
//		}
//		return sortedMap;
//	}
//
//	private static String appendZeros(final int previous, final int next,
//			String str) {
//		for (int i = 1; i <= (next - previous); i++) {
//			str += "0,";
//		}
//		return str.substring(0, str.length() - 1);
//	}
//
//	private static String binaryString(final int previous, final int next,
//			String str) {
//		for (int i = 1; i < (next - previous); i++) {
//			str += "0,";
//		}
//		str += 1 + ",";
//
//		return str;
//	}
//
//}
