# GitHub Copilot Instructions - Quick Reference

## 🎯 Quick Rules

### ✅ DO
- Make ONLY requested changes
- Use Constants for values
- Add proper logging
- Use best practices
- Ask for clarification if unclear

### ❌ DON'T
- Create .md files (unless asked)
- Commit or push code
- Create test files (unless asked)
- Add unnecessary classes
- Hardcode configuration values

---

## 📁 File Locations

| Purpose | Location |
|---------|----------|
| Constants | `src/main/java/hey/chat/config/Constants.java` |
| Controllers | `src/main/java/hey/chat/controller/` |
| Entities | `src/main/java/hey/chat/entity/` |
| DTOs | `src/main/java/hey/chat/dto/` |
| Repositories | `src/main/java/hey/chat/repository/` |
| Config | `src/main/java/hey/chat/config/` |
| Config Files | `src/main/resources/application.yaml` |

---

## 🔧 Common Code Patterns

### Logger Setup
```java
private static final Logger logger = LoggerFactory.getLogger(ClassName.class);
```

### Using Constants
```java
logger.info(Constants.LOG_MESSAGE_RECEIVED);
throw new RuntimeException(Constants.MSG_ROOM_NOT_FOUND);
```

### Optional Pattern
```java
Optional<Room> room = roomRepository.findByRoomId(roomId);
if (room.isPresent()) {
    // process
}
```

### Logging Example
```java
logger.info("Room found - RoomId: {}, MessageCount: {}", roomId, messages.size());
```

---

## 📋 Response Format

**After making changes:**
1. Brief summary of what was changed
2. File names modified
3. NO documentation creation
4. NO git operations

**Example**:
```
✅ Updated ChatController.java
- Added logging for message publishing
- Used Constants.LOG_MESSAGE_PUBLISHED
```

---

## 🚀 Full Instructions Available

For complete guidelines, see: `.copilot-instructions.md`

---

**Remember**: Only do what's asked. Keep it simple. Let the user handle git and docs.

