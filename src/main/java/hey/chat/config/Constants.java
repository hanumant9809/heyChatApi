package hey.chat.config;

/**
 * Global constants for heyChatApi application
 * All constant values are centralized here to avoid magic strings and numbers throughout the codebase
 */
public class Constants {

    // ==================== API Endpoints ====================
    public static final String API_CONTEXT_PATH = "/hey-chat/api";
    public static final String WEBSOCKET_ENDPOINT = "/ws";
    public static final String HEALTH_ENDPOINT = "/health";
    public static final String CHAT_ENDPOINT = "/chat";

    // ==================== WebSocket Configuration ====================
    public static final String TOPIC_PREFIX = "/topic";
    public static final String TOPIC_ROOM_FORMAT = "/topic/room/%s";
    public static final String APP_PREFIX = "/app";
    public static final String MESSAGE_MAPPING_FORMAT = "/sendMessage/%s";
    public static final String STOMP_SUBSCRIBE_FORMAT = "/topic/room/%s";

    // ==================== Database Configuration ====================
    public static final String DB_DEFAULT_HOST = "localhost";
    public static final String DB_DEFAULT_PORT = "5432";
    public static final String DB_DEFAULT_NAME = "heychatdb";
    public static final String DB_DEFAULT_USERNAME = "postgres";
    public static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String DB_HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

    // ==================== Database DDL Modes ====================
    public static final String HIBERNATE_DDL_AUTO_UPDATE = "update";
    public static final String HIBERNATE_DDL_AUTO_VALIDATE = "validate";
    public static final String HIBERNATE_DDL_AUTO_CREATE = "create";

    // ==================== CORS Configuration ====================
    public static final String CORS_ALLOWED_ORIGIN = "https://heyychat11.netlify.app";
    public static final String CORS_ALLOWED_ORIGIN_LOCAL = "http://localhost:3000";
    public static final String[] CORS_ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"};
    public static final int CORS_MAX_AGE = 3600;

    // ==================== Error Messages ====================
    public static final String MSG_ROOM_NOT_FOUND = "Room not found";
    public static final String MSG_ROOM_ALREADY_EXISTS = "Room already exists";
    public static final String MSG_INVALID_ROOM_ID = "Invalid room ID";
    public static final String MSG_MESSAGE_SAVE_FAILED = "Failed to save message";
    public static final String MSG_CONNECTION_SUCCESS = "Connected successfully";
    public static final String MSG_CONNECTION_FAILED = "Connection failed";

    // ==================== Success Messages ====================
    public static final String MSG_ROOM_CREATED = "Room created successfully";
    public static final String MSG_MESSAGE_SENT = "Message sent successfully";

    // ==================== Logging Separators ====================
    public static final String LOG_SEPARATOR = "===";
    public static final String LOG_SECTION_START = "=== %s ===";
    public static final String LOG_SUBSECTION = "--|";

    // ==================== Logging Messages ====================
    public static final String LOG_WEBSOCKET_CONNECT = "WebSocket CONNECT";
    public static final String LOG_WEBSOCKET_SUBSCRIBE = "WebSocket SUBSCRIBE";
    public static final String LOG_WEBSOCKET_SEND = "WebSocket SEND";
    public static final String LOG_WEBSOCKET_UNSUBSCRIBE = "WebSocket UNSUBSCRIBE";
    public static final String LOG_WEBSOCKET_DISCONNECT = "WebSocket DISCONNECT";
    public static final String LOG_MESSAGE_RECEIVED = "WebSocket Message Received";
    public static final String LOG_MESSAGE_PUBLISHING = "Message Publishing to Topic";
    public static final String LOG_MESSAGE_PUBLISHED = "Message Successfully Published";
    public static final String LOG_CONFIGURING_BROKER = "Configuring Message Broker";
    public static final String LOG_REGISTERING_ENDPOINTS = "Registering STOMP Endpoints";

    // ==================== Client Connecting ====================
    public static final String LOG_CLIENT_CONNECTING = "Client connecting to WebSocket endpoint";
    public static final String LOG_CLIENT_SUBSCRIBING = "Client subscribing to topic";
    public static final String LOG_CLIENT_DISCONNECTED = "Client disconnected from WebSocket";
    public static final String LOG_TOPIC_CREATED_ACCESSED = "TOPIC CREATED/ACCESSED";
    public static final String LOG_TOPIC_ACTIVE = "Room topic is now active for receiving messages";
    public static final String LOG_TOPIC_UNSUBSCRIBED = "Client unsubscribed from topic";

    // ==================== Database Operations ====================
    public static final String LOG_ROOM_FOUND = "Room found in database";
    public static final String LOG_ROOM_NOT_FOUND_LOG = "Room not found in database";
    public static final String LOG_MESSAGE_SAVED = "Message saved to database";
    public static final String LOG_ROOM_UPDATED = "Room updated in database with new message";
    public static final String LOG_MESSAGE_ASSOCIATED = "Associating message with room";

    // ==================== Application Properties ====================
    public static final String APP_NAME = "heyChatApi";
    public static final String APP_VERSION = "0.0.1-SNAPSHOT";

    // ==================== HTTP Status Messages ====================
    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_CONFLICT = 409;
    public static final int STATUS_INTERNAL_ERROR = 500;

    // ==================== Regular Expressions ====================
    public static final String REGEX_ROOM_ID_PATTERN = "^[a-zA-Z0-9_-]+$";
    public static final String REGEX_EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    // ==================== Limits & Sizes ====================
    public static final int MAX_MESSAGE_LENGTH = 1000;
    public static final int MAX_ROOM_NAME_LENGTH = 255;
    public static final int MAX_SENDER_NAME_LENGTH = 255;
    public static final int POOL_SIZE_MAX = 10;
    public static final int POOL_SIZE_MIN = 5;
    public static final int CONNECTION_TIMEOUT_MS = 20000;

    // ==================== Timeouts ====================
    public static final int SOCKET_TIMEOUT_MS = 30000;
    public static final int READ_TIMEOUT_MS = 15000;

    // ==================== Paths ====================
    public static final String API_ROOM_CREATE = "/room/create";
    public static final String API_ROOM_JOIN = "/room/join";
    public static final String API_ROOM_MESSAGES = "/room/{roomId}/messages";
    public static final String API_CREATE_MESSAGE = "/createMessage";

    /**
     * Private constructor to prevent instantiation
     * This is a utility class with only static constants
     */
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants utility class");
    }
}

