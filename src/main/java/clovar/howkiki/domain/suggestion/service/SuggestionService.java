package clovar.howkiki.domain.suggestion.service;

import clovar.howkiki.domain.store.entity.Store;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.domain.suggestion.dto.SuggestionDetailResponseDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionListResponseDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionRequestDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionResponseDto;
import clovar.howkiki.domain.suggestion.entity.Suggestion;
import clovar.howkiki.domain.suggestion.repository.SuggestionRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static clovar.howkiki.global.exception.ErrorCode.STORE_ID_NOT_FOUND;
import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SuggestionService {

    private final StoreRepository storeRepository;
    private final SuggestionRepository suggestionRepository;

    /* 건의사항 등록 */
    @Transactional
    public SuggestionDetailResponseDto createSuggestion(Long storeId, SuggestionRequestDto requestDto) {
        // 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/suggestions";
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));

        // 객체 생성
        Suggestion suggestion =  Suggestion.builder()
                .store(store)
                .content(requestDto.getContent())
                .build();
        // 저장
        Suggestion savedSuggestion = suggestionRepository.save(suggestion);

        return SuggestionDetailResponseDto.from(savedSuggestion);

    }

    /* 건의사항 목록 조회 */
    public SuggestionListResponseDto getAllSuggestions(Long storeId) {
        // 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/suggestions/all";
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));

        // 해당 가게의 건의사항들 리스트화
        List<SuggestionResponseDto> suggestionList = suggestionRepository.findAllByStoreId(storeId)
                .stream()
                .map(SuggestionResponseDto::from)
                .toList();

        return SuggestionListResponseDto.builder()
                .storeId(storeId)
                .suggestionList(suggestionList)
                .build();
    }
}
