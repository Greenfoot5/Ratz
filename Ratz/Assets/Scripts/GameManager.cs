using UnityEngine;
using UnityEngine.Tilemaps;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    public Tilemap walls;
    
    // Start is called before the first frame update
    private void Awake()
    {
        if (Instance != null)
        {
            Debug.LogError("Already a GameManager in the scene!");
            Destroy(gameObject);
        }

        Instance = this;
    }

    public bool isWall(Vector3 position)
    {
        var intPos = new Vector3Int((int) position.x, (int) position.y);
        return walls.GetTile(intPos) != null;
    }
}
