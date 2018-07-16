package com.redian.chat.service;

import com.redian.chat.domain.Message;
import com.redian.chat.domain.MessageDTO;
import com.redian.chat.domain.MessageQuery;
import com.redian.chat.domain.User;
import com.redian.chat.repository.MessageRepository;
import com.redian.chat.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 获取消息列表
     *
     * @param query
     * @return
     */
    public Page<Message> findAllByQuery(MessageQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());

        return messageRepository.findAll(new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Long lastId = query.getLastId();
                if (lastId != null) {
                    Path<Long> idPath = root.get("id");

                    Integer direction = query.getDirection();
                    if (direction != null && direction >= 0) {
                        cq.where(cb.greaterThanOrEqualTo(idPath, lastId));
                    } else {
                        cq.where(cb.lessThan(idPath, lastId));
                    }
                }

                Long userId1 = query.getUserId1();
                Long userId2 = query.getUserId2();
                if (userId1 != null && userId2 != null) {
                    Path<User> senderPath = root.get("sender");
                    Path<User> receiverPath = root.get("receiver");

                    Predicate p1 = cb.and(cb.equal(senderPath, userId1), cb.equal(receiverPath, userId2));
                    Predicate p2 = cb.and(cb.equal(senderPath, userId2), cb.equal(receiverPath, userId1));
                    cq.where(cb.or(p1, p2));
                }

                return cq.getRestriction();
            }
        }, pageable);
    }

    /**
     * 发送消息
     *
     * @param dto
     */
    @Transactional
    public void sendMessage(MessageDTO dto) {
        User sendUser = userRepository.findByOpenId(dto.getSender().getOpenId());
        if (sendUser == null) {
            sendUser = new User();
        }
        BeanUtils.copyProperties(dto.getSender(), sendUser);
        userRepository.save(sendUser);

        User receiveUser = userRepository.findByOpenId(dto.getReceiver().getOpenId());
        if (receiveUser == null) {
            receiveUser = new User();
        }
        BeanUtils.copyProperties(dto.getReceiver(), receiveUser);
        userRepository.save(receiveUser);

        Message message = new Message();
        message.setSender(sendUser);
        message.setReceiver(receiveUser);
        message.setContent(dto.getContent());
        message.setStatus(0);
        messageRepository.save(message);

    }
}
