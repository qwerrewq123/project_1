package spring.myproject.domain.gathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.myproject.domain.gathering.Gathering;
import spring.myproject.dto.response.gathering.GatheringPagingQueryDto;
import spring.myproject.dto.response.gathering.GatheringPagingResponse;
import spring.myproject.dto.response.gathering.GatheringQueryDto;

import java.util.List;
import java.util.Optional;

public interface GatheringRepository extends JpaRepository<Gathering,Long> {

    @Query("select new spring.myproject.dto.response.gathering." +
            "GatheringPagingQueryDto(g.id,g.title,g.content,g.registerDate,ca.name,cr.username,im.url,gc.count) " +
            "from Gathering  g " +
            "left join  g.category ca " +
            "left join  g.createBy cr " +
            "left join  g.gatheringImage im " +
            "left join  g.gatheringCount gc " +
            "where g.title like %:title%")
    Page<GatheringPagingQueryDto> findPaging(Pageable pageable, String title);

    @Query("select new spring.myproject.dto.response.gathering." +
            "GatheringPagingQueryDto(g.id,g.title,g.content,g.registerDate,ca.name,cr.username,im.url,gc.count) " +
            "from Like l  " +
            "join l.gathering g " +
            "left join g.category ca " +
            "left join g.createBy cr " +
            "left join g.gatheringImage im " +
            "left join g.gatheringCount gc " +
            "where l.likedBy.id = :userId")
    Page<GatheringPagingQueryDto> findLikePaging(Pageable pageable, Long userId);

    @Query("select new spring.myproject.dto.response.gathering." +
            "GatheringQueryDto(g.id,g.title,g.content,g.registerDate,ca.name,cr.username,u.username,im.url,gc.count) " +
            "from Recommend r " +
            "join r.gathering g " +
            "left join Enrollment e on e.gathering.id = g.id left join User u on u.id = e.enrolledBy.id " +
            "join g.category ca " +
            "join g.createBy cr " +
            "join g.gatheringImage im " +
            "join g.gatheringCount gc")
    List<GatheringQueryDto> findRecommendPaging();

    @Query("select " +
            "new spring.myproject.dto.response.gathering." +
            "GatheringQueryDto(g.id,g.title,g.content,g.registerDate,ca.name,cr.username,u.username,im.url,gc.count) " +
            "from Gathering g left join g.enrollments e left join e.enrolledBy u " +
            "left join g.createBy cr " +
            "left join g.category ca " +
            "left join g.gatheringImage im " +
            "left join g.gatheringCount gc " +
            "where g.id = :gatheringId")
    List<GatheringQueryDto> findGatheringAndCount(Long gatheringId);


}
