package blog.api.tag.dao;

import blog.api.tag.model.entity.QPostTagMapping;
import blog.api.tag.model.entity.QTag;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.response.BestTagsResponse;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {

    QTag tag = QTag.tag;
    QPostTagMapping postTagMapping = QPostTagMapping.postTagMapping;

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public List<BestTagsResponse> getBestTagsByTagNoCount(long limit) {
        return from(postTagMapping)
                .select(
                        Projections.bean(BestTagsResponse.class,
                                tag.name.as("name"),
                                postTagMapping.tag.count().as("count")))
                .leftJoin(postTagMapping.tag, tag)
                .groupBy(postTagMapping.tag)
                .orderBy(postTagMapping.tag.count().desc())
                .limit(limit)
                .fetch();
    }
}
