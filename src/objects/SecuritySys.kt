package objects

class SecuritySys(var ports: Array<Port>, var password: String, var hint: String, var isLocked: Boolean, var hintCount: Int = 3)