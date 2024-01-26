package vttp2023.batch4.paf.assessment.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
		db.listings.distinct("address.suburb") 
	 */
	public List<String> getSuburbs(String country) {
		
		Query query = new Query();
		return template.findDistinct(query, "address.suburb", "listings", String.class);
	}

	/*
	 	db.your_collection_name.find({
			"address.suburb": { $regex: suburb, $options: "i" }, 
			"price": { $lte: priceRange },                     
			"accommodates": { $gte: persons },                  
			"min_nights": { $lte: duration }                    
			{
				"_id": 1,
				"name": 1,
				"accommodates": 1,
				"price": 1                                     
			}).sort({ "price": -1 });                         
	 */
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {

		Criteria criteria = new Criteria();

		if (suburb != null && !suburb.isEmpty()) {
			criteria = criteria.and("address.suburb").regex(suburb, "i");
		}

		criteria = criteria.and("accommodates").gte(persons)
						.and("min_nights").lte(duration);

		Query query = new Query(criteria);
		query.fields().include("price").include("name").include("accommodates"); // include required fields

		List<Document> documents = template.find(query, Document.class, "listings");
		List<AccommodationSummary> results = new ArrayList<>();

		for (Document doc : documents) {
			float price = doc.get("price", Number.class).floatValue();
			if (price <= priceRange) {
				AccommodationSummary summary = new AccommodationSummary();
				summary.setId(doc.getString("_id"));
				summary.setName(doc.getString("name"));
				summary.setAccomodates(doc.getInteger("accommodates"));
				summary.setPrice(price);
				results.add(summary);
			}
		}
		return results;
	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
