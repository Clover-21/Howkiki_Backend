Use howkiki_db;

-- 1. Store 테이블에 초기 데이터 삽입
INSERT INTO stores (store_name, store_status, created_at, modified_at) VALUES
('호우섬', 'AVAILABLE', NOW(), NOW());

-- 2. Menu 테이블 초기 데이터 삽입
UPDATE menu 
SET created_at = NOW(), modified_at = NOW();

INSERT INTO menu (store_Id, menu_name, cost, menu_category, menu_status, menu_img_url)
VALUES 
(1, '소롱포', 7500, '딤섬', 'AVAILABLE', NULL),
(1, '통새우 쇼마이', 9500, '딤섬', 'AVAILABLE', NULL),
(1, '블랙 하가우', 9500, '딤섬', 'AVAILABLE', NULL),
(1, '트러플 차슈 바오', 8000, '딤섬', 'AVAILABLE', NULL),
(1, '호우섬 완탕', 9500, '딤섬', 'AVAILABLE', NULL),

(1, '마늘칩 꿔바육', 24500, '메인메뉴', 'AVAILABLE', NULL),
(1, '홍콩식 닭날개 튀김', 19000, '메인메뉴', 'AVAILABLE', NULL),
(1, '쯔란 갑오징어 튀김', 19500, '메인메뉴', 'AVAILABLE', NULL),

(1, '파이황과', 5000, '사이드 메뉴', 'AVAILABLE', NULL),
(1, '홍콩식 배추찜', 8000, '사이드 메뉴', 'AVAILABLE', NULL),
(1, '공심채 볶음', 8500, '사이드 메뉴', 'AVAILABLE', NULL),

(1, '라구짜장 & 계란 튀김 뽀짜이판', 15000, '뽀짜이판', 'AVAILABLE', NULL),
(1, '돼지고기 & 마파두부 뽀짜이판', 16500, '뽀짜이판', 'AVAILABLE', NULL),
(1, '새우 & 돼지고기 완자 뽀짜이판', 17500, '뽀짜이판', 'AVAILABLE', NULL),
(1, '우육 간장 조림 뽀짜이판', 18000, '뽀짜이판', 'AVAILABLE', NULL),

(1, '맑은 우육탕면', 13900, '면', 'AVAILABLE', NULL),
(1, '매운 소고기탕면', 14900, '면', 'AVAILABLE', NULL),
(1, '마파두부 도삭면', 11000, '면', 'AVAILABLE', NULL),
(1, '라구짜장 도삭면', 9900, '면', 'AVAILABLE', NULL),

(1, '호우섬 밀크티', 6000, '음료', 'AVAILABLE', NULL),
(1, '호우섬 레몬티', 6000, '음료', 'AVAILABLE', NULL),
(1, '코카콜라', 4000, '음료', 'AVAILABLE', NULL),
(1, '사이다', 4000, '음료', 'AVAILABLE', NULL),
(1, '호우섬 홍차 맥주', 7500, '음료', 'AVAILABLE', NULL),
(1, '타이거 맥주', 7500, '음료', 'AVAILABLE', NULL);
