# First
Main에 직접 push "절대" 금지

# Second
dev에도 웬만하면 직접 push 금지, 기능은 [feature/* branch] 에서 작업하는걸 권장

# Third
작업 전엔 git checkout dev / git pull origin dev / git checkout -b feature/* 습관화 하기
작업 후엔 git add . / git commit -m "feat: 블라블라블라" / git push origin feature/*
추후에 Github에서 feature에서 dev로 Pull Request 생성
feature branch Naming은 각 기능 or class 맞게 할 것

# Fourth
commit 메세지 양식은 통일합시다! Ex) feat: 로그인 화면 추가, fix: 시간 충돌 계산 오류 수정, refactor: lecture model 정리, docs: update readme
