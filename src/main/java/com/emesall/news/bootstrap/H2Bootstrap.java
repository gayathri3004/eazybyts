package com.emesall.news.bootstrap;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emesall.news.model.Category;
import com.emesall.news.model.Feed;
import com.emesall.news.model.User;
import com.emesall.news.model.UserList;
import com.emesall.news.model.WebSite;
import com.emesall.news.repository.CategoryRepository;
import com.emesall.news.repository.FeedRepository;
import com.emesall.news.repository.UserListRepository;
import com.emesall.news.repository.UserRepository;
import com.emesall.news.repository.WebSiteRepository;

@Component
@Transactional
@Profile("default")
public class H2Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final FeedRepository feedRepository;
	private final PasswordEncoder encoder;
	private final WebSiteRepository webSiteRepository;
	private final UserListRepository userListRepository;

	@Autowired
	public H2Bootstrap(UserRepository userRepository, CategoryRepository categoryRepository,
			FeedRepository feedRepository, PasswordEncoder encoder, WebSiteRepository webSiteRepository,UserListRepository userListRepository) {
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.feedRepository = feedRepository;
		this.encoder = encoder;
		this.webSiteRepository = webSiteRepository;
		this.userListRepository=userListRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		
		try {
			addUser();
			addCategories();
			addWebsites();
			addList();
			//createRandomFeed(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private void addList() {
		UserList list=new UserList();
		list.getWebSites().add(webSiteRepository.findById(1L).get());
		list.getWebSites().add(webSiteRepository.findById(2L).get());
		list.setUser(userRepository.findById(1L).get());
		list.setName("List1");
		userListRepository.save(list);
		
		UserList list2=new UserList();
		list2.getWebSites().add(webSiteRepository.findById(3L).get());
		list2.setUser(userRepository.findById(1L).get());
		list2.setName("List2");
		userListRepository.save(list2);
		
		UserList list3=new UserList();
		list3.getWebSites().add(webSiteRepository.findById(1L).get());
		list3.setUser(userRepository.findById(2L).get());
		list3.setName("List3");
		userListRepository.save(list3);
	}
	
	private void addUser() {
		User user1 = User.builder()
				.enabled(true)
				.email("user1@mail.com")
				.password(encoder.encode("user1"))
				.build();
		userRepository.save(user1);
		
		User user2 = User.builder()
				.enabled(true)
				.email("user2@mail.com")
				.password(encoder.encode("user2"))
				.build();
		
		userRepository.save(user2);
	}

	private void addWebsites() throws MalformedURLException {
		webSiteRepository.save(createWebSite("https://www.skysports.com/rss/12040", "Sport"));
		webSiteRepository.save(createWebSite("https://www.90min.com/posts.rss", "Sport"));
		webSiteRepository.save(createWebSite("https://www.dailymail.co.uk/news/articles.rss", "News"));
		webSiteRepository.save(createWebSite("https://feeds.washingtonpost.com/rss/world", "News"));
		webSiteRepository.save(createWebSite("https://feeds.a.dj.com/rss/WSJcomUSBusiness.xml", "Finance"));
		webSiteRepository.save(createWebSite("https://www.dailymail.co.uk/money/index.rss", "Finance"));
		webSiteRepository.save(createWebSite("https://www.dailymail.co.uk/health/index.rss", "Health"));
		webSiteRepository.save(createWebSite("https://www.insider.co.uk/all-about/economy/?service=rss", "Business"));
		webSiteRepository.save(createWebSite("https://www.business-live.co.uk/?service=rss", "Business"));
		webSiteRepository.save(createWebSite("https://www.techradar.com/rss", "Technology"));
		webSiteRepository.save(createWebSite("https://www.wired.com/feed/category/science/latest/rss", "Technology"));
		webSiteRepository.save(createWebSite("https://pitchfork.com/feed/feed-news/rss", "Music"));
		webSiteRepository.save(createWebSite("https://www.music-news.com/rss/UK/news?includeCover=true", "Music"));
		webSiteRepository.save(createWebSite("https://www.dailymail.co.uk/femail/food/index.rss", "Food"));
		webSiteRepository.save(createWebSite("https://www.dailymail.co.uk/travel/index.rss", "Travel"));
		
		

	}
	
	private WebSite createWebSite(String urlName,String catName) throws MalformedURLException {
		WebSite page = new WebSite();
		URL url = new URL(urlName);
		page.setUrl(url);
		page.setCategory(categoryRepository.findByName(catName).get());
		return page;
	}
	
	private Category createCategory(String name) {
		Category cat1 = new Category();
		cat1.setName(name);
		return cat1;
	}

	private void addCategories() {
		
		categoryRepository.save(createCategory("Sport"));
		categoryRepository.save(createCategory("News"));
		categoryRepository.save(createCategory("Finance"));
		categoryRepository.save(createCategory("Health"));
		categoryRepository.save(createCategory("Business"));
		categoryRepository.save(createCategory("Technology"));
		categoryRepository.save(createCategory("Music"));
		categoryRepository.save(createCategory("Food"));
		categoryRepository.save(createCategory("Travel"));
	
	}

	private void createRandomFeed() throws URISyntaxException {
        String title[] = {
            "England won over Netherlands",
            "NASA's New Mars Rover",
            "Global Economic Forecast",
            "Breakthrough in Cancer Research",
            "New Species Discovered",
            "Tech Giants Merge",
            "Climate Change Summit",
            "Historic Space Mission",
            "New Smartphone Released",
            "Olympic Games Highlights"
        };

        String content[] = {
            "England shattered their own world record for the highest total in ODI cricket in the first one-dayer against Netherlands at Amstelveen on Friday (June 17). England batsman ran amok and scored 498 for 4 in 50 overs with three batsmen reaching hundreds. England beat their own record of 481 for 6 which they scored against Australia in 2018 at Nottingham.",
            "NASA's new Mars rover, Perseverance, successfully landed on the red planet's surface and has begun its mission to search for signs of ancient life and collect samples of rock and soil for future return to Earth.",
            "The global economic forecast for the next quarter indicates a steady recovery with growth driven by strong consumer spending and industrial output, despite lingering uncertainties due to the pandemic.",
            "Researchers have announced a breakthrough in cancer treatment, with a new therapy showing promising results in early clinical trials, potentially offering a more effective and less invasive option for patients.",
            "A team of scientists has discovered a new species of deep-sea fish in the Pacific Ocean, expanding our understanding of marine biodiversity and the adaptations of life in extreme environments.",
            "Two leading tech companies, TechCorp and InnovateInc, have announced a merger that is expected to reshape the industry, combining their strengths to accelerate innovation and market reach.",
            "The Climate Change Summit concluded with world leaders agreeing on a comprehensive plan to reduce carbon emissions, promote renewable energy, and support sustainable development to combat global warming.",
            "The historic space mission Artemis I is set to launch, aiming to return humans to the Moon and lay the groundwork for future manned missions to Mars, marking a significant milestone in space exploration.",
            "The latest smartphone model from FutureTech has been released, featuring cutting-edge technology, an advanced camera system, and a sleek design, setting a new standard in the mobile industry.",
            "The Olympic Games in Tokyo concluded with memorable highlights, including record-breaking performances, inspiring stories of athletes, and a celebration of international unity and sportsmanship."
        };

        for (int i = 0; i < title.length; i++) {

            URI url = new URI("/");
            Feed feed = Feed.builder()
                    .author("Author" + i)
                    .instant(Instant.now())
                    .entry(content[i])
                    .title(title[i])
                    .uri(url)
                    .build();
            List<Category> categories = categoryRepository.findAll();
            feed.getCategories().addAll(categories);
            feedRepository.save(feed);

        }
    }

}