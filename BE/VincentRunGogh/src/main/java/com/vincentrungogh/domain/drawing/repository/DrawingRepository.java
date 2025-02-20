package com.vincentrungogh.domain.drawing.repository;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.entity.EachMonthRouteFreeCount;
import com.vincentrungogh.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrawingRepository extends JpaRepository<Drawing, Integer>, DrawingRepositoryCustom {

    List<Drawing> findAllByUser(User user);

    List<Drawing> findAllByUserId(int userId);

    List<Drawing> findAllByUserAndIsCompleted(User user, Boolean isCompleted);
//    List<Drawing> findAllByUserAndIsCompletedAndTitleIsNotNullOrderByCreated(User user, Boolean isCompleted);

    List<Drawing> findAllByUserAndIsCompletedAndTitleIsNotNullOrderByUpdatedDesc(User user, Boolean isCompleted);


    List<Drawing> findAllByUserAndIsCreatedBoard(User user, Boolean isCreatedBoard);

    int countAllByUserAndIsCompletedAndTitleIsNotNull(User user, Boolean isCompleted);

    @Override
    List<EachMonthRouteFreeCount> findRouteFreeCountByYearEachMonth(User user, int year);

    Optional<Drawing> findById(int drawingId);

    Optional<Drawing> findByIdAndUserId(int drawingId, int userId);
}
