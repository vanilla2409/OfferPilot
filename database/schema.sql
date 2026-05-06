-- OfferPilot Database Schema
-- Run this script to initialize the database

CREATE DATABASE IF NOT EXISTS offerpilot;
USE offerpilot;

-- Users
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('USER', 'ADMIN') DEFAULT 'USER',
    avatar_url  TEXT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Coding Questions
CREATE TABLE IF NOT EXISTS coding_questions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     TEXT NOT NULL,
    difficulty      ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
    topic           VARCHAR(100),
    examples        TEXT,
    constraints     TEXT,
    starter_code    TEXT,
    solution_code   TEXT,
    hints           TEXT,
    tags            VARCHAR(500),
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Interview Sessions (for both coding + HR)
CREATE TABLE IF NOT EXISTS interview_sessions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    session_type    ENUM('CODING', 'HR') NOT NULL,
    question_id     BIGINT,
    hr_topic        VARCHAR(200),
    user_code       TEXT,
    user_answer     TEXT,
    status          ENUM('IN_PROGRESS', 'COMPLETED', 'ABANDONED') DEFAULT 'IN_PROGRESS',
    started_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at    DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES coding_questions(id) ON DELETE SET NULL
);

-- AI Feedback for sessions
CREATE TABLE IF NOT EXISTS session_feedback (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id          BIGINT NOT NULL UNIQUE,
    overall_score       INT,
    correctness_score   INT,
    efficiency_score    INT,
    communication_score INT,
    strengths           TEXT,
    improvements        TEXT,
    detailed_feedback   TEXT,
    created_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES interview_sessions(id) ON DELETE CASCADE
);

-- Resumes
CREATE TABLE IF NOT EXISTS resumes (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    file_name       VARCHAR(255),
    file_path       TEXT,
    analysis_result TEXT,
    score           INT,
    uploaded_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- DSA Roadmap progress
CREATE TABLE IF NOT EXISTS dsa_progress (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    topic       VARCHAR(100) NOT NULL,
    status      ENUM('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'NOT_STARTED',
    score       INT DEFAULT 0,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_topic (user_id, topic),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Performance metrics (aggregated per user per day)
CREATE TABLE IF NOT EXISTS performance_metrics (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id             BIGINT NOT NULL,
    metric_date         DATE NOT NULL,
    coding_sessions     INT DEFAULT 0,
    hr_sessions         INT DEFAULT 0,
    avg_coding_score    DECIMAL(5,2) DEFAULT 0,
    avg_hr_score        DECIMAL(5,2) DEFAULT 0,
    questions_solved    INT DEFAULT 0,
    UNIQUE KEY unique_user_date (user_id, metric_date),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Seed: Sample coding questions
INSERT INTO coding_questions (title, description, difficulty, topic, examples, constraints, starter_code, hints, tags) VALUES
('Two Sum', 'Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.', 'EASY', 'Arrays',
 '[{"input": "nums = [2,7,11,15], target = 9", "output": "[0,1]"}]',
 '2 <= nums.length <= 104\n-109 <= nums[i] <= 109',
 'class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        // Your code here\n    }\n}',
 '["Try using a HashMap to store complement values", "For each number, check if target - number exists in the map"]',
 'array,hashmap,easy'),

('Valid Parentheses', 'Given a string s containing just the characters (, ), {, }, [ and ], determine if the input string is valid.', 'EASY', 'Stack',
 '[{"input": "s = \"()[]{}\"", "output": "true"}]',
 '1 <= s.length <= 10^4',
 'class Solution {\n    public boolean isValid(String s) {\n        // Your code here\n    }\n}',
 '["Use a stack data structure", "Push opening brackets, pop when closing bracket is found"]',
 'stack,string,easy'),

('Binary Search', 'Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums.', 'EASY', 'Binary Search',
 '[{"input": "nums = [-1,0,3,5,9,12], target = 9", "output": "4"}]',
 '1 <= nums.length <= 10^4',
 'class Solution {\n    public int search(int[] nums, int target) {\n        // Your code here\n    }\n}',
 '["Use left and right pointers", "Calculate mid = left + (right - left) / 2"]',
 'binary-search,array,easy'),

('Longest Substring Without Repeating Characters', 'Given a string s, find the length of the longest substring without repeating characters.', 'MEDIUM', 'Sliding Window',
 '[{"input": "s = \"abcabcbb\"", "output": "3"}]',
 '0 <= s.length <= 5 * 10^4',
 'class Solution {\n    public int lengthOfLongestSubstring(String s) {\n        // Your code here\n    }\n}',
 '["Use sliding window technique", "Maintain a set of characters in current window"]',
 'sliding-window,string,medium'),

('Merge Intervals', 'Given an array of intervals, merge all overlapping intervals.', 'MEDIUM', 'Sorting',
 '[{"input": "intervals = [[1,3],[2,6],[8,10],[15,18]]", "output": "[[1,6],[8,10],[15,18]]"}]',
 '1 <= intervals.length <= 10^4',
 'class Solution {\n    public int[][] merge(int[][] intervals) {\n        // Your code here\n    }\n}',
 '["Sort intervals by start time", "Compare current interval end with next interval start"]',
 'sorting,array,medium'),

('LRU Cache', 'Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.', 'HARD', 'Design',
 '[{"input": "capacity = 2, operations: put(1,1), put(2,2), get(1), put(3,3), get(2)", "output": "1,-1"}]',
 '1 <= capacity <= 3000',
 'class LRUCache {\n    public LRUCache(int capacity) {}\n    public int get(int key) {}\n    public void put(int key, int value) {}\n}',
 '["Use a combination of HashMap and Doubly Linked List", "HashMap for O(1) access, DLL for O(1) insertion/deletion"]',
 'design,hashmap,linked-list,hard');
