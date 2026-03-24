local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local current_time = tonumber(ARGV[3])

local data = redis.call("GET", key)

local tokens
local last_refill

if not data then
    tokens = capacity
    last_refill = current_time
else
    local parts = {}
    for token in string.gmatch(data, "[^:]+") do
        table.insert(parts, token)
    end

    tokens = tonumber(parts[1])
    last_refill = tonumber(parts[2])

    local delta = current_time - last_refill
    local refill = math.floor(delta / 60000 * refill_rate)

    tokens = math.min(capacity, tokens + refill)
end

if tokens > 0 then
    tokens = tokens - 1
    redis.call("SET", key, tokens .. ":" .. current_time, "EX", 120)
    return 1
else
    return 0
end