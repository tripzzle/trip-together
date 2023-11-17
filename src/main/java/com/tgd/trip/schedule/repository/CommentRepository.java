package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
