# Ekiden 数据源隔离方案

## 架构设计

### 数据源隔离
- **Gelatoni 主数据源**：`spring.datasource.gelatoni`（带 @Primary 注解）
- **Ekiden 第二数据源**：`spring.datasource.ekiden`（独立配置）

### 包结构隔离
```
infrastructure/repository/
├── mapper/                          # Gelatoni 主数据源 Mapper
├── entity/                          # Gelatoni 主数据源 Entity
├── impl/                            # Gelatoni 主数据源 Repository 实现
├── ekiden/                          # Ekiden 数据源隔离包
│   ├── mapper/                      # Ekiden Mapper（扫描路径）
│   ├── entity/                      # Ekiden Entity
│   ├── *Repository.java             # Ekiden Repository 接口
```

### 配置隔离
- **EkidenDataSourceConfig.java**
  - 数据源：`ekidenDataSource`
  - SqlSessionFactory：`ekidenSqlSessionFactory`
  - SqlSessionTemplate：`ekidenSqlSessionTemplate`
  - Mapper 扫描：`com.csxuhuan.gelatoni.infrastructure.repository.ekiden.mapper`

- **GelatoniDataSourceConfig.java**
  - 数据源：`gelatoniDataSource`（@Primary）
  - SqlSessionFactory：`gelatoniSqlSessionFactory`（@Primary）
  - SqlSessionTemplate：`gelatoniSqlSessionTemplate`（@Primary）
  - Mapper 扫描：`com.csxuhuan.gelatoni.infrastructure.repository.mapper`

## 创建的文件

### Entity 类（7个）
- `ekiden/entity/RaceDO.java` - 比赛（届数）
- `ekiden/entity/RunnerDO.java` - 选手
- `ekiden/entity/TeamDO.java` - 队伍
- `ekiden/entity/RaceResultDO.java` - 比赛成绩
- `ekiden/entity/RunnerCareerDO.java` - 选手履历
- `ekiden/entity/RunnerRelationshipDO.java` - 选手关系
- `ekiden/entity/RunnerTagDO.java` - 选手标签

### Mapper 接口（7个）
- `ekiden/mapper/RaceMapper.java`
- `ekiden/mapper/RunnerMapper.java`
- `ekiden/mapper/TeamMapper.java`
- `ekiden/mapper/RaceResultMapper.java`
- `ekiden/mapper/RunnerCareerMapper.java`
- `ekiden/mapper/RunnerRelationshipMapper.java`
- `ekiden/mapper/RunnerTagMapper.java`

### Repository 接口（7个）
- `ekiden/RaceRepository.java`
- `ekiden/RunnerRepository.java`
- `ekiden/TeamRepository.java`
- `ekiden/RaceResultRepository.java`
- `ekiden/RunnerCareerRepository.java`
- `ekiden/RunnerRelationshipRepository.java`
- `ekiden/RunnerTagRepository.java`

## 使用方式

### 注入 Ekiden Repository
```java
@Autowired
private RunnerRepository runnerRepository;

@Autowired
private RaceRepository raceRepository;
```

### 使用示例
```java
// 查询选手
RunnerDO runner = runnerRepository.selectById(1L);

// 新增比赛
RaceDO race = new RaceDO();
race.setEdition(100);
race.setYear(2024);
raceRepository.insert(race);
```

## 隔离优势

1. **数据源完全隔离**：两个数据源独立配置，互不影响
2. **包路径隔离**：Ekiden 相关代码集中在 `ekiden` 包下，便于维护
3. **Mapper 扫描隔离**：各数据源的 Mapper 扫描路径独立，避免冲突
4. **命名规范一致**：保持与 Gelatoni 数据源相同的代码风格和命名规范
