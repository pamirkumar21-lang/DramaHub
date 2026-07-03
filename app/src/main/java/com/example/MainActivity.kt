package com.example

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.*
import kotlinx.coroutines.delay

// Video data class
data class VideoItem(
    val id: String,
    val title: String,
    val description: String,
    val genre: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val duration: String,
    val views: String,
    val releaseYear: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KDramaAppScreen()
            }
        }
    }
}

@Composable
fun KDramaAppScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    // Sample high-quality video list with real mp4 streams and beautiful Unsplash KDrama-themed placeholder thumbnails
    val videos = remember {
        listOf(
            VideoItem(
                id = "1",
                title = "Crash Landing on You (Hindi Dubbed)",
                description = "An aristocratic paraglider pilot accidentally enters North Korea in a storm and falls in love with a noble soldier who resolves to hide and protect her at all costs.",
                genre = "Romance",
                videoUrl = "https://drive.google.com/uc?export=download&id=1lNN00qLdRX9hN6srz5dUu-4SxzFaGUF1",
                thumbnailUrl = "https://images.unsplash.com/photo-1518834107812-67b0b7c58434?q=80&w=600",
                duration = "1:15:32",
                views = "4.2M views",
                releaseYear = "2024"
            ),
            VideoItem(
                id = "2",
                title = "Descendants of the Sun (Hindi)",
                description = "A sweeping romance between a charismatic Captain in the Special Forces and an idealistic female surgeon as they navigate dangerous peacekeeping duties in a war-torn land.",
                genre = "Romance",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1492691527719-9d1e07e534b4?q=80&w=600",
                duration = "58:40",
                views = "2.8M views",
                releaseYear = "2023"
            ),
            VideoItem(
                id = "3",
                title = "The Flower of Evil (Hindi Dubbed)",
                description = "A chilling dark mystery. A detective wife discovers that her perfect, doting husband is hiding a sinister secret identity connected to a series of cold serial murders.",
                genre = "Thriller",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?q=80&w=600",
                duration = "1:02:15",
                views = "5.1M views",
                releaseYear = "2024"
            ),
            VideoItem(
                id = "4",
                title = "Kingdom of Shadows (Hindi Dubbed)",
                description = "A crown prince of Joseon investigates a mysterious plague ravaging his kingdom, encountering terrifying zombies and deep government conspiracies.",
                genre = "Thriller",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1509248961158-e54f6934749c?q=80&w=600",
                duration = "45:12",
                views = "1.9M views",
                releaseYear = "2024"
            ),
            VideoItem(
                id = "5",
                title = "Strong Girl Bong-Soon (Hindi)",
                description = "A cheerful woman born with mythological superhuman physical strength is hired as a private bodyguard by an eccentric, wealthy gaming corporation chief executive officer.",
                genre = "Comedy",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1513151233558-d860c5398176?q=80&w=600",
                duration = "1:08:44",
                views = "3.7M views",
                releaseYear = "2023"
            ),
            VideoItem(
                id = "6",
                title = "Welcome to Waikiki (Hindi)",
                description = "A hilarious riot. Three failing filmmakers run a bankrupt guest house in Seoul and face endless financial crises when a mysterious abandoned infant appears.",
                genre = "Comedy",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?q=80&w=600",
                duration = "52:10",
                views = "6.5M views",
                releaseYear = "2024"
            ),
            VideoItem(
                id = "7",
                title = "Vagabond Stuntman (Hindi Dubbed)",
                description = "After a tragic plane crash kills his nephew, a stuntman becomes determined to unearth a massive international web of deep military and political corruption.",
                genre = "Action",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1533928298208-27ff66555d8d?q=80&w=600",
                duration = "1:12:05",
                views = "8.2M views",
                releaseYear = "2025"
            ),
            VideoItem(
                id = "8",
                title = "Healer: Shadow Agent (Hindi)",
                description = "An illegal night courier with elite martial combat skills falls in love with a quirky internet journalist while uncovering long-buried media scandals.",
                genre = "Action",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=600",
                duration = "1:05:30",
                views = "3.1M views",
                releaseYear = "2024"
            )
        )
    }

    // Categories list
    val genres = remember { listOf("All", "Romance", "Thriller", "Comedy", "Action") }
    var selectedGenre by remember { mutableStateOf("All") }
    
    // Active playback state
    var activeVideo by remember { mutableStateOf<VideoItem?>(null) }
    var isFullscreen by remember { mutableStateOf(false) }

    // Tab state & saved bookmarks state
    var currentTab by remember { mutableStateOf("Home") }
    val bookmarkedIds = remember { mutableStateListOf("1", "3") }

    // Filter videos by genre
    val filteredVideos = remember(selectedGenre, videos) {
        if (selectedGenre == "All") videos else videos.filter { it.genre.equals(selectedGenre, ignoreCase = true) }
    }

    // Fullscreen Immersive Layout
    if (isFullscreen && activeVideo != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            CustomVideoPlayer(
                videoItem = activeVideo!!,
                isFullscreen = true,
                onToggleFullscreen = { isFullscreen = false },
                onClosePlayer = {
                    activeVideo = null
                    isFullscreen = false
                }
            )
        }
    } else {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    onSearchClick = { currentTab = "Discover" },
                    onProfileClick = { /* Click action profile */ }
                )
            },
            bottomBar = {
                CustomBottomNavigation(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it }
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    "Home" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            // 1. Interactive Video Player Card at the top if a video is active
                            if (activeVideo != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(240.dp)
                                        .shadow(8.dp)
                                        .background(Color.Black)
                                ) {
                                    CustomVideoPlayer(
                                        videoItem = activeVideo!!,
                                        isFullscreen = false,
                                        onToggleFullscreen = { isFullscreen = true },
                                        onClosePlayer = { activeVideo = null }
                                    )
                                }
                                
                                // Show current playing details
                                VideoDetailsCard(
                                    videoItem = activeVideo!!,
                                    onClose = { activeVideo = null }
                                )
                            } else {
                                // Cinematic Hero Cover Banner (Uses our high quality generated image!)
                                HeroBanner(
                                    onPlayTrending = {
                                        // Play the first video (Crash Landing on You) as the featured video
                                        activeVideo = videos.firstOrNull()
                                    }
                                )
                            }

                            // 2. Categories Row
                            Text(
                                text = "Explore Genres",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            )
                            
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(genres) { genre ->
                                    val isSelected = selectedGenre == genre
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { selectedGenre = genre },
                                        label = {
                                            Text(
                                                text = when (genre) {
                                                    "Romance" -> "Romance ❤️"
                                                    "Thriller" -> "Thriller 🔪"
                                                    "Comedy" -> "Comedy 🎭"
                                                    "Action" -> "Action ⚔️"
                                                    else -> genre
                                                },
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = RedPrimary,
                                            selectedLabelColor = PolishOnPrimary,
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        border = null,
                                        shape = RoundedCornerShape(20.dp),
                                        modifier = Modifier.testTag("genre_chip_$genre")
                                    )
                                }
                            }

                            // 3. KDrama Video Grid/List
                            Text(
                                text = if (selectedGenre == "All") "Hindi Dubbed Releases" else "$selectedGenre Specials",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            )

                            LazyColumn(
                                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                items(filteredVideos) { video ->
                                    KDramaVideoCard(
                                        video = video,
                                        isPlayingNow = activeVideo?.id == video.id,
                                        isBookmarked = bookmarkedIds.contains(video.id),
                                        onBookmarkToggle = {
                                            if (bookmarkedIds.contains(video.id)) {
                                                bookmarkedIds.remove(video.id)
                                            } else {
                                                bookmarkedIds.add(video.id)
                                            }
                                        },
                                        onClick = {
                                            activeVideo = video
                                        }
                                    )
                                }
                            }
                        }
                    }
                    "Discover" -> {
                        DiscoverScreen(
                            videos = videos,
                            onVideoClick = { video ->
                                activeVideo = video
                                currentTab = "Home"
                            },
                            bookmarkedIds = bookmarkedIds,
                            onBookmarkToggle = { id ->
                                if (bookmarkedIds.contains(id)) bookmarkedIds.remove(id) else bookmarkedIds.add(id)
                            }
                        )
                    }
                    "My List" -> {
                        MyListScreen(
                            videos = videos,
                            onVideoClick = { video ->
                                activeVideo = video
                                currentTab = "Home"
                            },
                            bookmarkedIds = bookmarkedIds,
                            onBookmarkToggle = { id ->
                                if (bookmarkedIds.contains(id)) bookmarkedIds.remove(id) else bookmarkedIds.add(id)
                            }
                        )
                    }
                    "Downloads" -> {
                        DownloadsScreen(
                            videos = videos,
                            onVideoClick = { video ->
                                activeVideo = video
                                currentTab = "Home"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBottomNavigation(
    currentTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        color = DarkSurface,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .navigationBarsPadding(),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF49454F).copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Triple("Home", Icons.Filled.Home, Icons.Outlined.Home),
                Triple("Discover", Icons.Filled.Explore, Icons.Outlined.Explore),
                Triple("My List", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
                Triple("Downloads", Icons.Filled.Download, Icons.Outlined.Download)
            )

            tabs.forEach { (tabName, filledIcon, outlinedIcon) ->
                val isSelected = currentTab == tabName
                
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(tabName) }
                        )
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) Color(0xFF4A4458) else Color.Transparent)
                            .padding(horizontal = 20.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) filledIcon else outlinedIcon,
                            contentDescription = tabName,
                            tint = if (isSelected) RedPrimary else TextSecondaryDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = tabName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isSelected) RedPrimary else TextSecondaryDark
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(RedPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayCircle,
                    contentDescription = "Logo",
                    tint = PolishOnPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Text(
                text = "DramaHub",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = (-0.5).sp
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = TextPrimaryDark
                )
            }
            
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account",
                    tint = TextPrimaryDark
                )
            }
        }
    }
}

@Composable
fun DiscoverScreen(
    videos: List<VideoItem>,
    onVideoClick: (VideoItem) -> Unit,
    bookmarkedIds: List<String>,
    onBookmarkToggle: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredBySearch = remember(searchQuery, videos) {
        if (searchQuery.isBlank()) {
            videos
        } else {
            videos.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true) ||
                it.genre.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Hindi Dubbed KDramas...", color = TextSecondaryDark) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search icon",
                    tint = TextSecondaryDark
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear search",
                            tint = TextSecondaryDark
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                disabledContainerColor = DarkSurface,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Popular Searches",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        val trendingTags = listOf("Crash Landing", "Romance", "Thriller", "Action", "Comedy")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(trendingTags) { tag ->
                Surface(
                    color = DarkSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable {
                        searchQuery = tag
                    }
                ) {
                    Text(
                        text = tag,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (searchQuery.isEmpty()) "Recommended For You" else "Search Results (${filteredBySearch.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        if (filteredBySearch.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.SearchOff,
                        contentDescription = "No results",
                        tint = TextSecondaryDark,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No dramas found matching \"$searchQuery\"",
                        color = TextSecondaryDark,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(filteredBySearch) { video ->
                    KDramaVideoCard(
                        video = video,
                        isPlayingNow = false,
                        isBookmarked = bookmarkedIds.contains(video.id),
                        onBookmarkToggle = { onBookmarkToggle(video.id) },
                        onClick = { onVideoClick(video) }
                    )
                }
            }
        }
    }
}

@Composable
fun MyListScreen(
    videos: List<VideoItem>,
    onVideoClick: (VideoItem) -> Unit,
    bookmarkedIds: List<String>,
    onBookmarkToggle: (String) -> Unit
) {
    val bookmarkedVideos = remember(bookmarkedIds, videos) {
        videos.filter { bookmarkedIds.contains(it.id) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "My Watchlist",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        if (bookmarkedVideos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = "Empty list",
                            tint = RedPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your Watchlist is Empty",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Browse modern Romance, Thrillers, Action, or Comedy specials and click the bookmark button to save them here.",
                        color = TextSecondaryDark,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(bookmarkedVideos) { video ->
                    KDramaVideoCard(
                        video = video,
                        isPlayingNow = false,
                        isBookmarked = true,
                        onBookmarkToggle = { onBookmarkToggle(video.id) },
                        onClick = { onVideoClick(video) }
                    )
                }
            }
        }
    }
}

@Composable
fun DownloadsScreen(
    videos: List<VideoItem>,
    onVideoClick: (VideoItem) -> Unit
) {
    val downloadedItems = remember(videos) {
        videos.take(2).mapIndexed { index, item ->
            Pair(item, if (index == 0) "340 MB" else "290 MB")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Offline Library",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(downloadedItems) { (video, size) ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onVideoClick(video) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = video.thumbnailUrl,
                            contentDescription = video.title,
                            modifier = Modifier
                                .size(70.dp, 100.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = video.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = "Episode 1 • $size • Offline ready",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryDark
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Downloaded icon",
                                    tint = RedPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Completed",
                                    fontSize = 11.sp,
                                    color = RedPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        
                        IconButton(
                            onClick = { onVideoClick(video) },
                            modifier = Modifier
                                .background(RedPrimary, CircleShape)
                                .size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play offline",
                                tint = PolishOnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroBanner(onPlayTrending: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        // High quality generated image background
        Image(
            painter = painterResource(id = R.drawable.img_kdrama_cover),
            contentDescription = "Trending KDrama Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark gradient overlay for extreme readability and visual luxury
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // Banner content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.TrendingUp,
                    contentDescription = "Trending Icon",
                    tint = RedAccent,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "#1 TRENDING IN HINDI",
                    style = MaterialTheme.typography.labelSmall,
                    color = RedAccent,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Text(
                text = "Crash Landing on You",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Hindi Dubbed Season 1 • Superhit Romance",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = onPlayTrending,
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(36.dp)
                    .testTag("play_trending_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play Icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Play Now",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun VideoDetailsCard(videoItem: VideoItem, onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = RedPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "Hindi Dubbed",
                                color = RedAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = videoItem.genre,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = videoItem.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close description",
                        tint = Color.LightGray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            Text(
                text = videoItem.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(6.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = videoItem.views,
                    fontSize = 11.sp,
                    color = Color.LightGray
                )
                Text(
                    text = "Year: ${videoItem.releaseYear}",
                    fontSize = 11.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomVideoPlayer(
    videoItem: VideoItem,
    isFullscreen: Boolean,
    onToggleFullscreen: () -> Unit,
    onClosePlayer: () -> Unit
) {
    val context = LocalContext.current
    
    // Create and remember ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    // Observe player states
    var isPlaying by remember { mutableStateOf(false) }
    var playbackState by remember { mutableStateOf(Player.STATE_IDLE) }
    var currentPosition by remember { mutableStateOf(0L) }
    var totalDuration by remember { mutableStateOf(0L) }
    var volume by remember { mutableStateOf(0.8f) }
    var isMuted by remember { mutableStateOf(false) }
    var playbackSpeed by remember { mutableStateOf(1.0f) }
    var showControls by remember { mutableStateOf(true) }
    var doubleTapIndicator by remember { mutableStateOf<String?>(null) } // "<< 10s" or "10s >>"

    // Set up screen rotation logic for true fullscreen
    LaunchedEffect(isFullscreen) {
        val activity = context as? Activity
        if (isFullscreen) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            val window = activity?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    hide(WindowInsetsCompat.Type.systemBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            val window = activity?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
    }

    // Handle media item source updates
    LaunchedEffect(videoItem) {
        val mediaItem = MediaItem.fromUri(videoItem.videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    // Update listeners for state updates
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                playbackState = state
                totalDuration = exoPlayer.duration.coerceAtLeast(0L)
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
            
            // Safe reset orientation when leaving the screen
            val activity = context as? Activity
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            val window = activity?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
    }

    // Dynamic player parameters updates
    LaunchedEffect(volume, isMuted) {
        exoPlayer.volume = if (isMuted) 0f else volume
    }

    LaunchedEffect(playbackSpeed) {
        exoPlayer.setPlaybackSpeed(playbackSpeed)
    }

    // Auto-hide controls timer
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            delay(3500)
            showControls = false
        }
    }

    // Position progress tracking effect
    LaunchedEffect(isPlaying, playbackState) {
        while (isPlaying && playbackState == Player.STATE_READY) {
            currentPosition = exoPlayer.currentPosition
            totalDuration = exoPlayer.duration.coerceAtLeast(0L)
            delay(250)
        }
    }

    // Temporary skip indicator visual auto-hide
    LaunchedEffect(doubleTapIndicator) {
        if (doubleTapIndicator != null) {
            delay(650)
            doubleTapIndicator = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Native ExoPlayer View
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // Custom Jetpack Compose controls used
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Double-Tap to Rewind / Fast Forward overlay & toggle controls
        Row(modifier = Modifier.fillMaxSize()) {
            // Left region (Rewind double tap)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onDoubleClick = {
                            val newPos = (exoPlayer.currentPosition - 10000).coerceAtLeast(0L)
                            exoPlayer.seekTo(newPos)
                            currentPosition = newPos
                            doubleTapIndicator = "⏪ 10s"
                        },
                        onClick = { showControls = !showControls }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (doubleTapIndicator == "⏪ 10s") {
                    DoubleTapPulseView("⏪ 10s")
                }
            }

            // Right region (Fast Forward double tap)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onDoubleClick = {
                            val newPos = (exoPlayer.currentPosition + 10000).coerceAtMost(exoPlayer.duration)
                            exoPlayer.seekTo(newPos)
                            currentPosition = newPos
                            doubleTapIndicator = "10s ⏩"
                        },
                        onClick = { showControls = !showControls }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (doubleTapIndicator == "10s ⏩") {
                    DoubleTapPulseView("10s ⏩")
                }
            }
        }

        // Loading spinner during buffering or setup
        if (playbackState == Player.STATE_BUFFERING) {
            CircularProgressIndicator(
                color = RedPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
            )
        }

        // Beautiful, high fidelity customized Compose controller overlay
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f))
            ) {
                // Top controls bar (Title & Back / Close buttons)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClosePlayer,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .testTag("close_player_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Close playback",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = videoItem.title,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )

                    // Playback Speed Selector dropdown button
                    var showSpeedMenu by remember { mutableStateOf(false) }
                    Box {
                        IconButton(
                            onClick = { showSpeedMenu = true },
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Speed,
                                contentDescription = "Speed selector",
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = showSpeedMenu,
                            onDismissRequest = { showSpeedMenu = false },
                            modifier = Modifier.background(DarkSurface)
                        ) {
                            listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { speed ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(
                                            text = "${speed}x", 
                                            color = if (playbackSpeed == speed) RedAccent else Color.White,
                                            fontWeight = if (playbackSpeed == speed) FontWeight.Bold else FontWeight.Normal
                                        ) 
                                    },
                                    onClick = {
                                        playbackSpeed = speed
                                        showSpeedMenu = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Center main controller: Big Play/Pause/Replay Button
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (playbackState == Player.STATE_ENDED) {
                                exoPlayer.seekTo(0L)
                                exoPlayer.play()
                            } else {
                                if (isPlaying) exoPlayer.pause() else exoPlayer.play()
                            }
                        },
                        modifier = Modifier
                            .size(64.dp)
                            .background(RedPrimary, CircleShape)
                            .shadow(4.dp, CircleShape)
                            .testTag("player_center_play_button")
                    ) {
                        Icon(
                            imageVector = when {
                                playbackState == Player.STATE_ENDED -> Icons.Filled.Replay
                                isPlaying -> Icons.Filled.Pause
                                else -> Icons.Filled.PlayArrow
                            },
                            contentDescription = "Center Play Action",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Bottom control panel (Progress Seekbar, Mute, Volume slider, Fullscreen toggle, Timestamps)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Continuous progress slider (Seekbar)
                    val progress = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f
                    Slider(
                        value = progress,
                        onValueChange = { targetPercent ->
                            val targetPosition = (targetPercent * totalDuration).toLong()
                            exoPlayer.seekTo(targetPosition)
                            currentPosition = targetPosition
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = RedPrimary,
                            activeTrackColor = RedPrimary,
                            inactiveTrackColor = Color.Gray.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .testTag("player_seek_bar")
                    )

                    // Volume & duration details row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            // Volume control with mute button
                            IconButton(
                                onClick = { isMuted = !isMuted },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = when {
                                        isMuted -> Icons.Filled.VolumeOff
                                        volume < 0.4f -> Icons.Filled.VolumeDown
                                        else -> Icons.Filled.VolumeUp
                                    },
                                    contentDescription = "Volume mute toggler",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Smooth volume level slider
                            Slider(
                                value = volume,
                                onValueChange = {
                                    volume = it
                                    isMuted = false
                                },
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(20.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.White,
                                    activeTrackColor = Color.White,
                                    inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Timestamp tags
                            Text(
                                text = "${formatTime(currentPosition)} / ${formatTime(totalDuration)}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Fullscreen Toggle action
                        IconButton(
                            onClick = onToggleFullscreen,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("fullscreen_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (isFullscreen) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                                contentDescription = "Fullscreen toggler",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Simple double tap overlay pulse
@Composable
fun DoubleTapPulseView(text: String) {
    Surface(
        color = Color.Black.copy(alpha = 0.7f),
        shape = CircleShape,
        modifier = Modifier.size(72.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun KDramaVideoCard(
    video: VideoItem,
    isPlayingNow: Boolean,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlayingNow) DarkSurfaceVariant else DarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Video Thumbnail Box with Overlay duration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(video.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize().clickable(onClick = onClick),
                    contentScale = ContentScale.Crop
                )
                
                // Red theme translucent gradient filter
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                            )
                        )
                        .clickable(onClick = onClick)
                )

                // Bookmark / Watchlist Action icon at top-right
                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .size(36.dp)
                        .testTag("bookmark_toggle_${video.id}")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Toggle Watchlist",
                        tint = if (isBookmarked) RedPrimary else Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Playing visual overlay if chosen
                if (isPlayingNow) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RedPrimary.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            color = Color.Black.copy(alpha = 0.85f),
                            shape = CircleShape,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Playing visual",
                                    tint = RedAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "PLAYING NOW",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Duration tag
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = video.duration,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Romance / Thriller Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(RedPrimary, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = video.genre,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Info rows below thumbnail
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = video.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = "Views icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = video.views,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }

                    // Immersive styled button inside card
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPlayingNow) RedAccent else Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                        modifier = Modifier
                            .height(28.dp)
                            .testTag("play_button_${video.id}")
                    ) {
                        Text(
                            text = if (isPlayingNow) "Playing" else "Play",
                            color = if (isPlayingNow) Color.White else Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// Format milliseconds into 00:00 or 00:00:00 format
fun formatTime(milliseconds: Long): String {
    if (milliseconds <= 0) return "00:00"
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    val hours = (milliseconds / (1000 * 60 * 60)) % 24
    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
