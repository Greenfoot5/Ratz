using System;
using System.Collections.Generic;
using UnityEngine;
using Random = UnityEngine.Random;

public abstract class Rat : MonoBehaviour
{
    [SerializeField]
    [Tooltip("How long it takes the rat to move one unit")]
    protected int speed = 4;
    [SerializeField]
    protected int rotationSpeed;

    [SerializeField]
    [Tooltip("How much health the rat has before it dies")]
    protected int maxHealth = 6;
    private int _health;

    [SerializeField]
    [Tooltip("The gender of the rat")]
    protected Sex gender;

    [Tooltip("Which tile the rat is targeting next")]
    private Vector3 _target;
    // The distance to the tile's centre before it's considered reached
    private const float DistanceToTile = 0.05f;

    private const int RandomStartDelay = 6;
    // TODO - Change to a sound
    //private const sound DEATH_SOUND_PATH = "deathSound.mp3";

    public void Start()
    {
        _target = transform.position;
        _health = maxHealth;
        GetNextTarget();
    }

    public void Update()
    { 
        // If we aren't looking at our target, let's rotate!
        // Gets the rotation the turret need to end up at, and lerp each frame towards that
        Vector2 aimDir = ((Vector2)_target - (Vector2)transform.position).normalized;
        Vector3 up = transform.up;
        Vector3 lookDir = Vector3.Lerp(up, aimDir, Time.deltaTime * rotationSpeed);
        transform.rotation *= Quaternion.FromToRotation(up, lookDir);
        
        // Get the direction of travel
        Vector3 dir = _target - transform.position;;
        transform.Translate(dir.normalized * (speed * Time.deltaTime), Space.World);
        
        // TODO - Check if the rat hits a wall
        // If the enemy is within the set distance, get the next waypoint
        if (Vector3.Distance(transform.position, _target) <= DistanceToTile)
        {
            GetNextTarget();
        }
    }

    private void GetNextTarget()
    {
        // Is forward valid?
        Vector3 forwardPos = _target + transform.up;
        Vector3 leftPos = _target - transform.right;
        Vector3 rightPos = _target + transform.right;
        // switch((int) transform.rotation.y)
        // {
        //     case (int) Direction.North:
        //         forwardPos = _target + Vector3.up;
        //         rightPos = _target + Vector3.right;
        //         leftPos = _target + Vector3.left;
        //         break;
        //     case (int) Direction.East:
        //         forwardPos = _target + Vector3.right;
        //         rightPos = _target + Vector3.down;
        //         leftPos = _target + Vector3.up;
        //         break;
        //     case (int) Direction.South:
        //         forwardPos = _target + Vector3.down;
        //         rightPos = _target + Vector3.left;
        //         leftPos = _target + Vector3.right;
        //         break;
        //     case (int) Direction.West:
        //         forwardPos = _target + Vector3.left;
        //         rightPos = _target + Vector3.up;
        //         leftPos = _target + Vector3.down;
        //         break;
        //     default:
        //         throw new ArgumentOutOfRangeException();
        // }

        var validDirections = new List<Vector3>();
        if (!GameManager.Instance.isWall(forwardPos))
            validDirections.Add(forwardPos);
        if (!GameManager.Instance.isWall(leftPos))
            validDirections.Add(leftPos);
        if (!GameManager.Instance.isWall(rightPos))
            validDirections.Add(rightPos);
        
        // Check if the next location is a wall
        if (validDirections.Count > 0)
        {
            _target = validDirections[Random.Range(0, validDirections.Count)];
        }
        else
        {
            _target = _target - transform.forward;
        }
    }
}

/// <summary>
/// Represents a rat's gender
/// </summary>
public enum Sex
{
    Male,
    Female,
    Intersex
}

/// <summary>
/// Represents what direction a rat is travelling in
/// </summary>
public enum Direction
{
    North = 0,
    East = 90,
    South = 180,
    West = 270
}