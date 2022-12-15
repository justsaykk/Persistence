package day28workshop.day28.repositories;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import day28workshop.day28.models.GameRating;
import io.vavr.collection.List;

@Repository
public class ReviewRepo {

        @Autowired
        private MongoTemplate repo;

        public List<String> getReviewsforGame(int gid) {

                MatchOperation matchGid = Aggregation.match(
                                Criteria.where("gid").is(gid));

                LookupOperation lookupGid = Aggregation.lookup(
                                "Games", "gid", "gid", "result");

                ProjectionOperation projectFields = Aggregation.project("c_id").andExclude("_id");

                Aggregation pipeline = Aggregation.newAggregation(
                                matchGid, lookupGid, projectFields);

                AggregationResults<Document> results = repo.aggregate(
                                pipeline,
                                "Comments",
                                Document.class);

                java.util.List<String> listOfcid = new ArrayList<>();
                // List<String> listOfcid = List.empty();
                results.forEach(d -> {
                        // listOfcid.push(d.getString("c_id"));
                        listOfcid.add(d.getString("c_id"));
                });

                return List.ofAll(listOfcid);
        }

        public List<GameRating> getGamesByRating(Direction direction) {
                SortOperation sortOps = Aggregation.sort(direction, "rating");

                System.out.println("Direction >> " + direction.toString());

                LookupOperation lookupGid = Aggregation.lookup("Games", "gid", "gid",
                                "lookupresult");

                LimitOperation limitOps = Aggregation.limit(500);

                Document nameField = new Document("$arrayElemAt",
                                Arrays.asList("$lookupresult.name", 0L));
                AddFieldsOperation newFields = Aggregation.addFields()
                                .addFieldWithValueOf("name", nameField)
                                .build();

                ProjectionOperation projection = Aggregation
                                .project("rating", "gid", "user", "c_text", "c_id", "name")
                                .andExclude("_id");

                Aggregation pipeline = Aggregation.newAggregation(
                                sortOps,
                                lookupGid,
                                limitOps,
                                newFields,
                                projection);

                System.out.println("Beginning Aggregation Operations");

                AggregationResults<Document> results = repo.aggregate(
                                pipeline,
                                "Comments",
                                Document.class);

                System.out.println("Ending Aggregation Operations");

                java.util.List<GameRating> listOfGames = new ArrayList<>();
                // List<GameRating> listOfGames = new List<>();
                results.forEach(doc -> {
                        listOfGames.add(new GameRating(doc));
                        // listOfGames.push(new GameRating(doc));
                });

                // return listOfGames;
                return List.ofAll(listOfGames).distinctBy(GameRating::getGid);
        }
}