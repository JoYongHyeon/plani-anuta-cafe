# Git Branch Strategy & Workflow

본 프로젝트는 **단순화된 Git Flow 전략**을 기반으로 협업한다.
모든 팀원은 아래 규칙을 반드시 준수해 주세요.

---

## 브랜치 구조

```
main      → 운영 / 배포 기준 브랜치
develop   → 통합 개발 브랜치
feature/* → 기능 단위 개발 브랜치
hotfix/*  → 운영 긴급 수정 브랜치
```

---

## 브랜치별 역할

### `main` 브랜치
- 운영 및 배포 기준 브랜치
- **직접 push 절대 금지**
- 반드시 Pull Request(PR)를 통해서만 병합

### `develop` 브랜치
- 모든 기능이 통합되는 개발 브랜치
- `feature` 브랜치의 병합 대상
- 테스트가 완료된 안정화된 코드만 유지

### `feature/*` 브랜치
- 기능 단위 개발을 위한 브랜치
- 작업 시작 시 `develop` 브랜치를 기준으로 생성
- 작업 완료 후 `develop` 브랜치로 PR 생성
- **병합 완료 후 삭제 권장(하나의 feature = 하나의 목적)**

**브랜치 생성 예시:**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/login-api
```

### `hotfix/*` 브랜치
- 운영(`main`) 환경에서 발생한 긴급 버그 수정용
- 수정 완료 후 `main`과 `develop` 브랜치에 **모두 병합**

---

## 개발 및 병합 규칙

### 기본 원칙

1. **모든 개발은 `feature` 브랜치에서 진행**
2. **`main` 브랜치 직접 push 금지**
3. **Pull Request 병합 순서:**
    - `feature/*` → `develop`
    - `develop` → `main`
4. **모든 PR은 최소 1명 이상 리뷰 후 병합**

---

## Pull Request 규칙

### PR 생성 시 필수 포함 사항

- **변경 사항 요약:** 무엇을 수정했는지
- **주요 구현 내용:** 어떻게 구현했는지
- **테스트 여부:** 테스트 완료 여부

### 병합 방식
- 리뷰 승인 후 병합
- **Squash merge 권장** (커밋 히스토리 정리)

---

## 브랜치 생성 및 삭제 규칙

| 브랜치 | 생성 주체 | 생성 시점 |
|--------|----------|----------|
| `main` | 리더 | 프로젝트 시작 시 |
| `develop` | 리더 | 프로젝트 시작 시 |
| `feature/*` | 개발자 | 작업 시작 시 |
| `hotfix/*` | 리더/담당자 | 긴급 이슈 발생 시 |

- `main`, `develop` 브랜치는 프로젝트 시작 시 **리더가 미리 생성**
- `feature/*` 브랜치는 각 개발자가 **작업 시작 시 생성**
- 기능 병합 완료 후 `feature` 브랜치는 **삭제 권장**

---

## 브랜치 보호 정책

### `main` 브랜치
-  Pull Request 필수
-  최소 1명 이상 승인 필요
-  Direct push 차단

### `develop` 브랜치
- Pull Request 권장
- 상황에 따라 보호 설정 적용

---

## 커밋 메시지 규칙 (권장)

```
feat: 기능 추가
fix: 버그 수정
refactor: 리팩토링
docs: 문서 수정
test: 테스트 코드 추가
chore: 기타 작업
```

**예시:**
```bash
feat: 로그인 API 구현
fix: 주문 수량 계산 오류 수정
refactor: 결제 도메인 구조 개선
docs: Git Flow 문서 추가
```

---

## 목적

본 브랜치 전략의 목적은 다음과 같습니다:

-  운영 브랜치의 안정성 확보
-  코드 품질 향상
-  협업 과정의 명확화
-  리뷰 문화 정착

---

## 작업 흐름 요약

```
1. develop 브랜치에서 feature 브랜치 생성
   git checkout develop
   git pull origin develop
   git checkout -b feature/기능명

2. 기능 개발 및 커밋
   git add .
   git commit -m "feat: 기능 설명"

3. feature 브랜치를 원격에 push
   git push origin feature/기능명

4. GitHub에서 feature → develop PR 생성

5. 팀원 리뷰 및 승인

6. develop에 병합 후 feature 브랜치 삭제

7. develop이 충분히 안정화되면 main으로 PR 생성

8. main 배포
```

---

본 문서는 프로젝트 진행 상황에 따라 팀 합의 하에 수정될 수 있습니다.
